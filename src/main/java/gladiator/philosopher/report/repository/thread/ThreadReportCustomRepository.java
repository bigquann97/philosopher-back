package gladiator.philosopher.report.repository.thread;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import org.springframework.data.domain.Pageable;

public interface ThreadReportCustomRepository {

  MyPage<ThreadReportResponseDto> getThreadReports(ThreadReportSearchCondition condition, Pageable pageable);

}
