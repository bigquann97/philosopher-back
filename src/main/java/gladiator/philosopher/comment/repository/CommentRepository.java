package gladiator.philosopher.comment.repository;

import com.querydsl.jpa.impl.JPAQuery;
import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

  @Query("select new gladiator.philosopher.account.dto.CommentSimpleResponseDto(c.id,c.thread.title, c.content) from Comment c where c.account.id =:id order by c.id DESC ")
  Page<CommentSimpleResponseDto> getCommentsByAccount(@Param("id") Long accountId, Pageable pageable);

}