package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  /*
   * thread 로 전체 comment 조회
   *
   * @param thread
   * @return
   */
  List<Comment> findAllByThread(Thread thread);

  @Modifying
  @Transactional
  @Query(value = "UPDATE comment SET content = :content WHERE commentId = :commentId ", nativeQuery = true)
  void modify(Long commentId, String content);

  /*
   *  찐으로 해당 코멘트 쓴 사람 id찾기
   */
  @Transactional
  @Query(value = "SELECT account_id from ACCOUNT WHERE id = :id", nativeQuery = true)
  String findWriter(Long id);

  @Modifying
  @Transactional
  @Query(value = "DELETE FROM comment WHERE commentId = :commentId ", nativeQuery = true)
  void delete(String commentId);

}