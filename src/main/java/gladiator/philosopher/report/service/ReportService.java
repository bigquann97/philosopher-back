package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.dto.post.PostReportResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;

public interface ReportService {

  void reportPost(Post id, ReportRequestDto dto, Account member);

  void reportComment(Comment id, ReportRequestDto dto, Account member);

  void reportThread(Thread id, ReportRequestDto dto, Account member);
  
  List<ReportResponseDto> getReports();

  List<PostReportResponseDto> getPostReports();
}
