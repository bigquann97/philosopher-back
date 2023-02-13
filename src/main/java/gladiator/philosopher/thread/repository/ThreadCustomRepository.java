package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.domain.Page;

public interface ThreadCustomRepository {

  Page<ThreadResponseDto> getThreadsByCondition(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByRecommendation(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByViewCount(ThreadSearchCond cond);

//  Page<Thread> getThreads(ThreadSearchCond cond);

}
