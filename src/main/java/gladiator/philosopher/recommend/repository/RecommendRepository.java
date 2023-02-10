package gladiator.philosopher.recommend.repository;


import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

  Optional<Recommend> findByPostIdAndAccount(Long postId, Account account);

  Optional<Recommend> findByThreadIdAndAccount(Long threadId, Account account);

  Optional<Recommend> findByCommentIdAndAccount(Long commentId, Account account);

/*
  Long countByPostId(Long postId);

  Long countByThreadId(Long threadId);

  Long countByCommentId(Long commentId);
*/

  List<Recommend> findByPost(@NonNull Post post);

}
