package gladiator.philosopher.report.repository.post;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.PostReportSearchCondition;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import org.springframework.data.domain.Pageable;

public interface PostReportCustomRepository {

  MyPage<PostReportResponseDto> getPostReports(PostReportSearchCondition condition, Pageable pageable);

}
