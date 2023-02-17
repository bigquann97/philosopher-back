package gladiator.philosopher.report.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.entity.ThreadReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ThreadReportRepository extends JpaRepository<ThreadReport, Long> {

  boolean existsByReporterAndThreadId(@NonNull Account reporter, @NonNull Long threadId);

  long countByThreadId(@NonNull Long threadId);


}
