package gladiator.philosopher.recommend.repository;

import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.dto.RecommendCommentResponseDto;
import gladiator.philosopher.account.dto.SimpleResponseDtoByThread;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.recommend.entity.ThreadRecommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThreadRecommendRepository extends JpaRepository<ThreadRecommend, Long> {

  boolean existsByThreadAndAccount(Thread thread, Account account);

  Optional<ThreadRecommend> findByThreadAndAccount(Thread thread, Account account);

  @Query("select new gladiator.philosopher.account.dto.SimpleResponseDtoByThread(tr.id, tr.thread.title, tr.thread.content, tr.thread.location) from ThreadRecommend  tr where tr.account.id=:id order by  tr.id DESC ")
  Page<SimpleResponseDtoByThread> getRecommendThreadsByAccount(@Param("id")Long accountId, Pageable pageable);

  @Query("select new gladiator.philosopher.account.dto.RecommendCommentResponseDto(cr.id, cr.comment.content) from CommentRecommend cr where cr.account.id=:id order by cr.id DESC ")
  Page<RecommendCommentResponseDto> findRecommendCommentsByAccount(@Param("id") Long accountId, Pageable pageable);
}
