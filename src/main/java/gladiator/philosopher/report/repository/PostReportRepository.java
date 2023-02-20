package gladiator.philosopher.report.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.post.PostReportResponseDto;
import gladiator.philosopher.report.entity.PostReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {


  @Query("select new gladiator.philosopher.report.dto.post.PostReportResponseDto(pr.id, pr.content, pr.category, pr.reporter.nickname, pr.postId)  from PostReport  pr order by pr.id desc ")
  List<PostReportResponseDto> getAllByPostReportResponseDto();

  boolean existsByReporterAndPostId(@NonNull Account reporter, @NonNull Long postId);

  long countByPostId(@NonNull Long postId);

}
