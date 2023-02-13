package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
<<<<<<< HEAD
import gladiator.philosopher.thread.entity.Thread;
=======
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import java.util.Optional;
>>>>>>> f2d8a8198dc422e08907684d1361c21319ed760d
import org.springframework.data.domain.Page;

public interface ThreadCustomRepository {

  Optional<ThreadResponseDto> selectThread(Long id);

  Page<ThreadSimpleResponseDto> selectActiveThreadsWithPaging(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByCondition(ThreadSearchCond cond);

  Page<ThreadSimpleResponseDto> selectArchivedThreadsWithPaging(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByRecommendation(ThreadSearchCond cond);

  Page<ThreadResponseDto> getThreadsByViewCount(ThreadSearchCond cond);

//  Page<Thread> getThreads(ThreadSearchCond cond);

}
