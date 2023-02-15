package gladiator.philosopher.report.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface ReportRepository extends JpaRepository<Report, Long> {

  boolean existsByReporterAndThreadId(@NonNull Account reporter, @NonNull Long threadId);
  
  boolean existsByReporterAndPostId(@NonNull Account reporter, @NonNull Long postId);

  boolean existsByReporterAndCommentId(@NonNull Account reporter, @NonNull Long commentId);

  long countByThreadId(@NonNull Long threadId);

  long countByPostId(@NonNull Long postId);

  long countByCommentId(@NonNull Long commentId);

  @Query("select new gladiator.philosopher.report.dto.ReportResponseDto(r.id,r.reported.nickname,r.reporter.nickname, r.category, r.postId, r.threadId, r.commentId, r.content) from Report r")
  List<ReportResponseDto> getReports();

}
