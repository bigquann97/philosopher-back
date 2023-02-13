package gladiator.philosopher.thread.service;

import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ThreadService {

  @Transactional
  Thread startThread(final Post post);

  @Transactional
  ThreadResponseDto selectThread(final Long threadId);

  @Transactional
  Page<ThreadSimpleResponseDto> selectActiveThreadsWithPaging(ThreadSearchCond cond);

  @Transactional
  Page<ThreadSimpleResponseDto> getArchivedThreads(ThreadSearchCond of);

  @Transactional
  Thread finishThread(final Thread thread);

  Thread getThreadEntity(Long id);

//  Page<ThreadsSimpleResponseDtoByAdmin> getThreads(ThreadSearchCond cond);

  List<ThreadsSimpleResponseDtoByAdmin> getThreadsV2();

}
