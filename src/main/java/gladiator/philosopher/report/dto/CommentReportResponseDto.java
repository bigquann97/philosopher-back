package gladiator.philosopher.report.dto;

import gladiator.philosopher.report.enums.ReportCategory;
import lombok.Getter;

@Getter
public class CommentReportResponseDto {

  private Long id;
  private String content;
  private ReportCategory category;
  private String reporter;
  private Long commentId;

  public CommentReportResponseDto(Long id, String content, ReportCategory category, String reporter,
      Long commentId) {
    this.id = id;
    this.content = content;
    this.category = category;
    this.reporter = reporter;
    this.commentId = commentId;
  }
}
