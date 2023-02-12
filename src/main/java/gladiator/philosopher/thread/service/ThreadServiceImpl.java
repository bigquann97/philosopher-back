package gladiator.philosopher.thread.service;

import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.notification.service.NotificationService;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadStatus;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

  private final ThreadRepository threadRepository;
  private final NotificationService notificationService;

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

    notificationService.notifyToRecommendersThatThreadHasStarted(post);

    return threadRepository.save(thread);
  }

  @Override
  @Transactional
  public Thread finishThread(final Thread thread) {
    Thread archivedThread = thread.finishThread();
    return threadRepository.save(archivedThread);
  }

  @Override
  @Transactional
  public Thread getThreadEntity(Long id) {
    return threadRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
  }

  @Override
  @Transactional
  public ThreadResponseDto selectThread(final Long threadId) {
    return threadRepository.selectThread(threadId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
  }

  @Override
  @Transactional
  public Page<ThreadSimpleResponseDto> selectActiveThreadsWithPaging(ThreadSearchCond cond) {
    return threadRepository.selectActiveThreadsWithPaging(
        cond);
  }

  @Override
  @Transactional
  public Page<ThreadSimpleResponseDto> getArchivedThreads(ThreadSearchCond cond) {
    Page<Thread> threads = threadRepository.findByStatus(ThreadStatus.ARCHIVED, cond.getPageable());
    return threads.map(thread -> ThreadSimpleResponseDto.builder().thread(thread).build());
  }

}
