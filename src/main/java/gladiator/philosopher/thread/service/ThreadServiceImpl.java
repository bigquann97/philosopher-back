package gladiator.philosopher.thread.service;

import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.service.RecommendService;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadStatus;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

  private final ThreadRepository threadRepository;
  private final RecommendService recommendService;

  @Override
  @Transactional
  public Thread startThread(final Post post) {
    Thread thread = Thread.builder()
        .account(post.getAccount())
        .title(post.getTitle())
        .content(post.getContent())
        .postImages(post.getImages())
        .startTime(LocalDateTime.now())
        .endTime(LocalDateTime.now().plusDays(1L))
        .build();

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
  public ThreadResponseDto getThread(final Long threadId) {
    Thread thread = threadRepository.findById(threadId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    return ThreadResponseDto.builder().thread(thread).build();
  }

  @Override
  @Transactional
  public Page<ThreadSimpleResponseDto> getActiveThreads() {
    PageRequest page = PageRequest.of(0, 0);
    Page<Thread> threads = threadRepository.findByStatus(ThreadStatus.CONTINUE, page);
    return threads.map(thread -> ThreadSimpleResponseDto.builder().thread(thread).build());
  }

  @Override
  @Transactional
  public Page<ThreadSimpleResponseDto> getArchivedThreads() {
    PageRequest page = PageRequest.of(0, 0);
    Page<Thread> threads = threadRepository.findByStatus(ThreadStatus.ARCHIVED, page);
    return threads.map(thread -> ThreadSimpleResponseDto.builder().thread(thread).build());
  }

}
