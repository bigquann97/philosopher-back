package gladiator.philosopher.report.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.entity.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

  boolean existsByReporterAndPostId(@NonNull Account reporter, @NonNull Long postId);

  long countByPostId(@NonNull Long postId);

}
