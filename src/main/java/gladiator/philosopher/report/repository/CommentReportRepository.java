package gladiator.philosopher.report.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.entity.CommentReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

  boolean existsByReporterAndCommentId(@NonNull Account reporter, @NonNull Long commentId);

  long countByCommentId(@NonNull Long commentId);


}
