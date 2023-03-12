package gladiator.philosopher.report.dto;

import gladiator.philosopher.report.enums.ReportCategory;
import lombok.Getter;

@Getter
public class PostReportResponseDto {

  private Long id;
  private String content;
  private ReportCategory category;
  private String reporter;
  private Long postId;

  public PostReportResponseDto(Long id, String content, ReportCategory category, String reporter,
      Long postId) {
    this.id = id;
    this.content = content;
    this.category = category;
    this.reporter = reporter;
    this.postId = postId;
  }

}
