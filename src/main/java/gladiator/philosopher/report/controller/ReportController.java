package gladiator.philosopher.report.controller;

import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportController {

  private final ReportService reportService;
  private final CommentService commentService;

  @PostMapping("/post")
  @ResponseStatus(HttpStatus.CREATED)
  public void reportPost(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    reportService.reportPost(id, dto, accountDetails.getAccount());
  }

  @PostMapping("/comment")
  @ResponseStatus(HttpStatus.CREATED)
  public void reportComment(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    reportService.reportComment(id, dto, accountDetails.getAccount());
  }

  // api/report/thread?id=3
  @PostMapping("/thread")
  @ResponseStatus(HttpStatus.CREATED)
  public void reportThread(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails) {
    reportService.reportThread(id, dto, accountDetails.getAccount());
  }

}
