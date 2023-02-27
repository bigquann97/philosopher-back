package gladiator.philosopher.report.dto;

import gladiator.philosopher.report.enums.ReportCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class PostReportSearchCondition {

  private ReportCategory reportCategory;

}
