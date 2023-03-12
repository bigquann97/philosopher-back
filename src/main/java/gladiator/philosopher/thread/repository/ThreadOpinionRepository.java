package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadOpinion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ThreadOpinionRepository extends JpaRepository<ThreadOpinion, Long> {

  List<ThreadOpinion> findByThread(@NonNull Thread thread);

}