package gladiator.philosopher.thread.repository;

import gladiator.philosopher.thread.entity.ThreadImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadImageRepository extends JpaRepository<ThreadImage, Long> {

}
