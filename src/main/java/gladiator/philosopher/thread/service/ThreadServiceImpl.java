package gladiator.philosopher.thread.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_POST;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_THREAD;

import gladiator.philosopher.admin.dto.ModifyThreadRequestDto;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.util.RedisUtil;
import gladiator.philosopher.notification.service.NotificationService;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadImage;
import gladiator.philosopher.thread.entity.ThreadOpinion;
import gladiator.philosopher.thread.repository.ThreadImageRepository;
import gladiator.philosopher.thread.repository.ThreadOpinionRepository;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThreadServiceImpl implements ThreadService {

  private final ThreadOpinionRepository threadOpinionRepository;

  private final ThreadRepository threadRepository;
  private final ThreadImageRepository threadImageRepository;
  private final NotificationService notificationService;
  private final RedisUtil redisUtil;
  public static final String THREAD_TIME_LIST_KEY = "THREAD::LOOK_UP";
  private final PostService postService;

  /**
   * 쓰레드 시작
   *
   * @param post
   * @return
   */
  @Override
  @Transactional
  public Thread startThread(final Post post, final List<PostRecommend> recommends) {
    Thread thread = Thread.builder()
        .account(post.getAccount())
        .title(post.getTitle())
        .content(post.getContent())
        .category(post.getCategory())
        .endDate(calculateEndDate())
        .build();

    Thread savedThread = threadRepository.saveAndFlush(thread);

    List<ThreadImage> images = postService.getPostImages(post).stream()
        .map(x -> new ThreadImage(x.getImageUrl(), thread)).
        collect(Collectors.toList());

    threadImageRepository.saveAll(images);

    List<ThreadOpinion> opinions = postService.getPostOpinions(post).stream()
        .map(o -> new ThreadOpinion(thread, o.getOpinion())).collect(Collectors.toList());

    threadOpinionRepository.saveAll(opinions);

    notificationService.notifyToRecommendersThatThreadHasStarted(savedThread, recommends);

    redisUtil.addHashData(THREAD_TIME_LIST_KEY, savedThread.getId().toString(),
        savedThread.getEndDate().toString());

    return savedThread;
  }

  private LocalDateTime calculateEndDate() {
    LocalDateTime time = LocalDateTime.now()
        .plusDays(2)
        .minusHours(LocalDateTime.now().getHour())
        .minusMinutes(LocalDateTime.now().getMinute());

    return LocalDateTime.of(
        time.getYear(),
        time.getMonthValue(),
        time.getDayOfMonth(),
        time.getHour(),
        time.getMinute()
    );
  }

  @Override
  @Transactional
  public Thread finishThread(final Thread thread) {
    Thread archivedThread = thread.finishThread();
    return threadRepository.save(archivedThread);
  }

  @Override
  @Transactional
  public Thread getThreadEntity(final Long id) {
    return threadRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_THREAD));
  }

  @Override
  @Transactional
  public ThreadResponseDto selectThread(final Long threadId) {
    return threadRepository.selectThread(threadId)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));
  }

  @Override
  @Transactional
  public MyPage<ThreadSimpleResponseDto> selectActiveThreads(final ThreadSearchCond cond) {
    return threadRepository.selectActiveThreadsWithCond(cond);
  }

  @Override
  @Transactional(readOnly = true)
  public ThreadResponseDto selectArchivedThread(Long threadId) {
    return threadRepository.selectThread(threadId)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_THREAD));
  }

  @Override
  @Transactional(readOnly = true)
  public MyPage<ThreadSimpleResponseDto> selectArchivedThreads(final ThreadSearchCond cond) {
    return threadRepository.selectArchivedThreadWithCond(cond);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ThreadOpinion> getOpinions(Thread thread) {
    return threadOpinionRepository.findByThread(thread);
  }

  @Override
  @Transactional(readOnly = true)
  public MyPage<ThreadsSimpleResponseDtoByAdmin> searchThreadByAdmin(
      final ThreadSearchCondByAdmin cond,
      final Pageable pageable
  ) {
    return threadRepository.selectThreadByAdmin(cond, pageable);
  }

}
