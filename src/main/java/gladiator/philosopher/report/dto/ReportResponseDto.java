package gladiator.philosopher.report.dto;

import gladiator.philosopher.report.entity.ReportCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReportResponseDto {

  private final Long id;
  private final String reportedAccount;
  private final String reportAccount;
  private final ReportCategory category;
  private final Long postId;
  private final Long threadId;
  private final Long commentId;
  private final String content;

}
