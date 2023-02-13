package gladiator.philosopher.report.controller;

import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

  private final ReportService reportService;

  // /api/report/post?id=10
  @PostMapping("/post")
  public void reportPost(
      @RequestParam Long id,
      @RequestBody ReportRequestDto dto,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    reportService.reportPost(id, dto, accountDetails.getAccount());
  }

  // /api/report/comment?id=3
  @PostMapping("/comment")
  public void reportComment(@RequestParam Long id, @RequestBody ReportRequestDto dto,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    reportService.reportComment(id, dto, accountDetails.getAccount());
  }

  // api/report/thread?id=3
  @PostMapping("/thread")
  public void reportThread(@RequestParam Long id, @RequestBody ReportRequestDto dto,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    reportService.reportThread(id, dto, accountDetails.getAccount());
  }

}
