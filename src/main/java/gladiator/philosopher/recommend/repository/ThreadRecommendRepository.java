package gladiator.philosopher.recommend.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.recommend.entity.ThreadRecommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRecommendRepository extends JpaRepository<ThreadRecommend, Long> {

  boolean existsByThreadAndAccount(Thread thread, Account account);

  Optional<ThreadRecommend> findByThreadAndAccount(Thread thread, Account account);

}
