package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ThreadCustomRepository {

  Optional<ThreadResponseDto> selectThread(Long id);

  Page<ThreadSimpleResponseDto> selectActiveThreadsWithPaging(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByCondition(ThreadSearchCond cond);

  Page<ThreadSimpleResponseDto> selectArchivedThreadsWithPaging(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByRecommendation(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByViewCount(ThreadSearchCond cond);
}
