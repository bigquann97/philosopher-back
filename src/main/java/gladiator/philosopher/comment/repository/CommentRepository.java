package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  /*
   * postId 로 전체 comment 조회
   *
   * @param post
   * @return
   */
  List<Comment> findAllByPost(Post post);
}