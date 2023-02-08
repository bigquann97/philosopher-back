package gladiator.philosopher.recommend.repository;


import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

  Optional<Recommend> findByPostIdAndAccount(Long postId, Account account);
  /*
  Optional<Recommend> findByThreadIdAndAccount(Long threadId, Account account);
  Optional<Recommend> findByCommentIdAndAccount(Long commentId, Account account);
  */
}
