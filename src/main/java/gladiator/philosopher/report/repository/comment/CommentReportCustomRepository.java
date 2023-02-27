package gladiator.philosopher.report.repository.comment;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.dto.CommentReportSearchCondition;
import org.springframework.data.domain.Pageable;

public interface CommentReportCustomRepository {

  MyPage<CommentReportResponseDto> getCommentReports(CommentReportSearchCondition condition, Pageable pageable);

}
