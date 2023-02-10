package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  /*
   * post, thread 로 전체 comment 조회
   *
   * @param post
   * @return
   */
  List<Comment> findAllByPostAndThread(Post post, Thread thread);
}