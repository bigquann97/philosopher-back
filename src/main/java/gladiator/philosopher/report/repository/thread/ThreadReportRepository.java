package gladiator.philosopher.report.repository.thread;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.entity.ThreadReport;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

public interface ThreadReportRepository extends JpaRepository<ThreadReport, Long>, ThreadReportCustomRepository{

  boolean existsByReporterAndThreadId(@NonNull Account reporter, @NonNull Long threadId);

  long countByThreadId(@NonNull Long threadId);

  @Query("select new gladiator.philosopher.report.dto.ThreadReportResponseDto(tr.id, tr.content, tr.category, tr.reporter.nickname, tr.threadId) from ThreadReport  tr order by tr.id DESC ")
  List<ThreadReportResponseDto> getAllThreadReportDtosByAdmin(Pageable pageable);


}
