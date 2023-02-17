package gladiator.philosopher.recommend.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.recommend.entity.CommentRecommend;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRecommendRepository extends JpaRepository<CommentRecommend, Long> {

  boolean existsByCommentAndAccount(Comment comment, Account account);

  Optional<CommentRecommend> findByCommentAndAccount(Comment comment, Account account);

}
