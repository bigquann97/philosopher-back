package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

  /*
   * thread 로 전체 comment 조회
   *
   * @param thread
   * @return
   */
  List<Comment> findAllByThread(Thread thread);

  Page<Comment> findCommentByAccount_Id(Long accountId, Pageable pageable);
}