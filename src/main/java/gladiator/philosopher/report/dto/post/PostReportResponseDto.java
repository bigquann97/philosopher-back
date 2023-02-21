package gladiator.philosopher.report.dto.post;

import gladiator.philosopher.report.entity.ReportCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
