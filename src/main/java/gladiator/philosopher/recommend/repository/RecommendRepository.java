package gladiator.philosopher.recommend.repository;

//import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

/*  Optional<Recommend> findByPostAndAccount(Post post, Account account);
  Optional<Recommend> findByThreadAndAccount(Thread thread, Account account);
  Optional<Recommend> findByCommentAndAccount(Comment comment, Account account);*/

}
