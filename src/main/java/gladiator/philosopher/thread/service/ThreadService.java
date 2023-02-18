package gladiator.philosopher.thread.service;

import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadOpinion;
import java.util.List;

public interface ThreadService {

  Thread startThread(final Post post, final List<PostRecommend> recommends);

  ThreadResponseDto selectThread(final Long threadId);

  MyPage<ThreadSimpleResponseDto> selectActiveThreads(final ThreadSearchCond cond);

  ThreadResponseDto selectArchivedThread(Long threadId);

  MyPage<ThreadSimpleResponseDto> selectArchivedThreads(final ThreadSearchCond of);

  Thread finishThread(final Thread thread);

  Thread getThreadEntity(final Long id);

  List<ThreadsSimpleResponseDtoByAdmin> getThreadsV2();

  void controllActiveThreads();

  List<ThreadOpinion> getOpinions(Thread thread);
}
