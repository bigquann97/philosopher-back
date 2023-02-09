package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

  Page<Thread> findByStatus(@NonNull ThreadStatus status, Pageable pageable);

}
