package gladiator.philosopher.thread.service;

import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ThreadService {

  @Transactional
  Thread startThread(final Post post);

  @Transactional
  ThreadResponseDto getThread(final Long threadId);

  @Transactional
  Page<ThreadSimpleResponseDto> getActiveThreads();

  @Transactional
  Page<ThreadSimpleResponseDto> getArchivedThreads();

  @Transactional
  Thread finishThread(final Thread thread);
}
