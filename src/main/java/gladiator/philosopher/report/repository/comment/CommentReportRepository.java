package gladiator.philosopher.report.repository.comment;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.entity.CommentReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long>, CommentReportCustomRepository {

  boolean existsByReporterAndCommentId(@NonNull Account reporter, @NonNull Long commentId);

  long countByCommentId(@NonNull Long commentId);

  @Query("select new gladiator.philosopher.report.dto.CommentReportResponseDto(cr.id, cr.content, cr.category, cr.reporter.nickname, cr.commentId)  from CommentReport cr ")
  List<CommentReportResponseDto> getAllCommentReportDtosByAdmin();


}
