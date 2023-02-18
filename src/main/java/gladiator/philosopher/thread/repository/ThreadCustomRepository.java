package gladiator.philosopher.thread.repository;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import java.util.Optional;

public interface ThreadCustomRepository {

  Optional<ThreadResponseDto> selectThread(Long id);

  MyPage<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond);

  MyPage<ThreadSimpleResponseDto> selectArchivedThreadWithCond(ThreadSearchCond cond);
}
