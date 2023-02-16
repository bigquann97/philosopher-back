package gladiator.philosopher.recommend.repository;


import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

  long countByPost(@NonNull Post post);

  boolean existsByCommentAndAccount(Comment comment, Account account);

  boolean existsByThreadAndAccount(Thread thread, Account account);

  boolean existsByPostAndAccount(Post post, Account account);

  Optional<Recommend> findByPostAndAccount(Post post, Account account);

  Optional<Recommend> findByThreadAndAccount(Thread thread, Account account);

  Optional<Recommend> findByCommentAndAccount(Comment comment, Account account);

  List<Recommend> findByPost(@NonNull Post post);

}
