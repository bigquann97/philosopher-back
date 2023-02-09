package gladiator.philosopher.mention.repository;

import gladiator.philosopher.mention.entity.Mention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentionRepository extends JpaRepository<Mention, Long> {

}
