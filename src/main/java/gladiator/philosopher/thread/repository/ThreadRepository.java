package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<Thread, Long> {

}
