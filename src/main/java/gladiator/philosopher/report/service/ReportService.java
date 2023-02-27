package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.dto.CommentReportSearchCondition;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.PostReportSearchCondition;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ReportService {

  void reportPost(Long id, ReportRequestDto dto, Account member);

  void reportComment(Long id, ReportRequestDto dto, Account member);

  void reportThread(Long id, ReportRequestDto dto, Account member);

  MyPage<PostReportResponseDto> getPostReports(PostReportSearchCondition condition, Pageable pageable);

  MyPage<CommentReportResponseDto> getCommentReports(CommentReportSearchCondition condition, Pageable pageable);

  MyPage<ThreadReportResponseDto> getThreadReports(ThreadReportSearchCondition condition, Pageable pageable);

}
