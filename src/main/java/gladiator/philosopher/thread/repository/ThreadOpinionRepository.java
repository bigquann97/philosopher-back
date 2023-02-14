package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.entity.ThreadOpinion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadOpinionRepository extends JpaRepository<ThreadOpinion, Long> {

}