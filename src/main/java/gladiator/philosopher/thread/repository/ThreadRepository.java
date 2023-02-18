package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadLocation;
import gladiator.philosopher.thread.entity.ThreadStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ThreadRepository extends JpaRepository<Thread, Long>, ThreadCustomRepository {

  Page<Thread> findByStatus(@NonNull ThreadLocation status, Pageable pageable);

  List<Thread> findAllByEndDateIsBeforeAndStatus(LocalDateTime endDate, ThreadStatus status);

//  @Query("select new gladiator.philosopher.thread.dto.ThreadSimpleResponseDto(t.id, t.title, count(r), t.account.nickname,t.createdDate, t.endTime) from Thread t join Recommend r on r.thread.id=t.id")
//  List<ThreadSimpleResponseDto> findAllThreads();

//  @Query("select t from Thread t join fetch t.account a join fetch t.postImages pi join fetch  t.recommends r")
//  List<Thread> findAllThreadsInfo();

}
