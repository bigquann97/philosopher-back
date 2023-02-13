package gladiator.philosopher.thread.service;

import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.notification.service.NotificationService;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadLocation;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ThreadServiceImpl implements ThreadService {

  private final ThreadRepository threadRepository;
  private final NotificationService notificationService;

  /**
   * 쓰레드 시작
   *
   * @param post
   * @return
   */
  @Override
  @Transactional
  public Thread startThread(final Post post) {
    List<PostImage> images = post.getImages();
    Thread thread = Thread.builder()
        .account(post.getAccount())
        .title(post.getTitle())
        .content(post.getContent())
        .postImages(images)
        .endDate(LocalDateTime.now().plusDays(1L))
        .build();
    
    notificationService.notifyToRecommendersThatThreadHasStarted(post, thread);

    return threadRepository.save(thread);
  }

  /**
   * 쓰레드 종료
   *
   * @param thread
   * @return
   */
  @Override
  @Transactional
  public Thread finishThread(final Thread thread) {
    Thread archivedThread = thread.finishThread();
    return threadRepository.save(archivedThread);
  }

  /**
   * 쓰레드 찾기 -> id to Entity
   *
   * @param id
   * @return
   */
  @Override
  @Transactional
  public Thread getThreadEntity(Long id) {
    return threadRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
  }

  /**
   * 쓰레드 dto 변환 -> id to Entity finish ThreadResponseDto
   *
   * @param threadId
   * @return
   */
  @Override
  @Transactional
  public ThreadResponseDto selectThread(final Long threadId) {
    return threadRepository.selectThread(threadId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
  }

  /**
   * 활성화 된 쓰레드 가지고 오기
   *
   * @param cond
   * @return
   */
  @Override
  @Transactional
  public Page<ThreadSimpleResponseDto> selectActiveThreadsWithPaging(ThreadSearchCond cond) {
    return threadRepository.selectActiveThreadsWithPaging(
        cond);
  }

  /**
   * 아카이브된 쓰레드 가지고 오기
   *
   * @param cond
   * @return
   */
  @Override
  @Transactional
  public Page<ThreadSimpleResponseDto> getArchivedThreads(ThreadSearchCond cond) {
    Page<Thread> threads = threadRepository.findByStatus(ThreadLocation.ARCHIVED,
        cond.getPageable());
    return threads.map(thread -> ThreadSimpleResponseDto.builder().thread(thread).build());
  }


  /**
   * 어드민쪽에서 사용할 threads
   *
   * @return
   */
//  @Override
//  public Page<ThreadsSimpleResponseDtoByAdmin> getThreads(ThreadSearchCond cond) {
//    List<ThreadsSimpleResponseDtoByAdmin> result = new ArrayList<>();
//    Page<Thread> threads = threadRepository.getThreads(cond);
//
//    for (Thread thread : threads) {
//      ThreadsSimpleResponseDtoByAdmin threadsSimpleResponseDtoByAdmin = new ThreadsSimpleResponseDtoByAdmin(thread);
//      result.add(threadsSimpleResponseDtoByAdmin);
//    }
//    return new PageImpl<>(result);
//  }
  @Override // join 관련해서 한번 고민해볼것.
  @Transactional(readOnly = true)
  public List<ThreadsSimpleResponseDtoByAdmin> getThreadsV2() {
//    List<Thread> infoData = threadRepository.findAllThreadsInfo();
    final List<Thread> all = threadRepository.findAll();
    List<ThreadsSimpleResponseDtoByAdmin> resultData = new ArrayList<>();
    all.forEach(entity -> {
      ThreadsSimpleResponseDtoByAdmin threadsSimpleResponseDtoByAdmin = new ThreadsSimpleResponseDtoByAdmin(
          entity);
      resultData.add(threadsSimpleResponseDtoByAdmin);
    });
    // 데이터가 있어야먄 join을 해온다는 점?
    return resultData;
  }

}
