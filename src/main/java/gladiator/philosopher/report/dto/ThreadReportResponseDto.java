package gladiator.philosopher.report.dto;

import gladiator.philosopher.report.enums.ReportCategory;
import lombok.Getter;

@Getter
public class ThreadReportResponseDto {

  private Long id;
  private String content;
  private ReportCategory category;
  private String reporter;
  private Long threadId;

  public ThreadReportResponseDto(Long id, String content, ReportCategory category, String reporter,
      Long threadId) {
    this.id = id;
    this.content = content;
    this.category = category;
    this.reporter = reporter;
    this.threadId = threadId;
  }
}
