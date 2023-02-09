package gladiator.philosopher.report.dto;

import gladiator.philosopher.report.entity.ReportCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ReportRequestDto {

  private final String content;

  private final ReportCategory reportCategory;

}
