package gladiator.philosopher.thread.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_POST;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_THREAD;

import gladiator.philosopher.account.dto.SimpleResponseDtoByThread;
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
import gladiator.philosopher.recommend.repository.ThreadRecommendRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThreadServiceImpl implements ThreadService {

  private final ThreadRecommendRepository threadRecommendRepository;
  private final ThreadOpinionRepository threadOpinionRepository;

  private final ThreadRepository threadRepository;
  private final ThreadImageRepository threadImageRepository;
  private final NotificationService notificationService;
  private final RedisUtil redisUtil;
  private final PostService postService;

  public static final String THREAD_TIME_LIST_KEY = "THREAD::LOOK_UP";

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

  @Override
  @Transactional
  public void finishThread(final Thread thread) {
    Thread archivedThread = thread.finishThread();
    threadRepository.save(archivedThread);
  }

  @Override
  @Transactional(readOnly = true)
  public Thread getThreadEntity(final Long id) {
    return threadRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_THREAD));
  }

  @Override
  @Transactional(readOnly = true)
  public ThreadResponseDto selectThread(final Long threadId) {
    Thread thread = getThreadEntity(threadId);
    if (thread.isBlinded() || thread.isDeleted()) {
      throw new NotFoundException(NOT_FOUND_THREAD);
    }
    return threadRepository.selectThread(threadId)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_POST));
  }

  @Override
  @Transactional(readOnly = true)
  public MyPage<ThreadSimpleResponseDto> selectActiveThreads(final ThreadSearchCond cond) {
    return threadRepository.selectActiveThreadsWithCond(cond);
  }

  @Override
  @Transactional(readOnly = true)
  public ThreadResponseDto selectArchivedThread(final Long threadId) {

    Thread thread = getThreadEntity(threadId);
    if (thread.isBlinded() || thread.isDeleted()) {
      throw new NotFoundException(NOT_FOUND_THREAD);
    }
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
  public List<ThreadOpinion> getOpinions(final Thread thread) {
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

  @Override
  @Transactional(readOnly = true)
  public MyPage<SimpleResponseDtoByThread> getRecommendThreadsByAccount(
      final Long accountId,
      final Pageable pageable
  ) {
    Page<SimpleResponseDtoByThread> data = threadRecommendRepository.getRecommendThreadsByAccount(
        accountId, pageable);
    return new MyPage<>(data);
  }

  @Override
  @Transactional
  public Long modifyThreadByAdmin(
      final Long id,
      final ModifyThreadRequestDto threadRequestDto,
      final Category category
  ) {
    final Thread thread = threadRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_THREAD));
    thread.modifyThread(threadRequestDto.getTitle(), threadRequestDto.getContent(), category);
    threadRepository.saveAndFlush(thread);
    return thread.getId();
  }

  // --- Private Methods ---

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

}
