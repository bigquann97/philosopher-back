package gladiator.philosopher.report.controller;

import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.service.ReportService;
import gladiator.philosopher.security.members.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportController {

  private final ReportService reportService;

  // /api/report/post?id=3
  @PostMapping("/post")
  public void reportPost(@RequestParam Long id,
      ReportRequestDto dto,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    reportService.reportPost(id, dto, memberDetails.getMember());
  }

  // /api/report/comment?id=3
  @PostMapping("/comment")
  public void reportComment(@RequestParam Long id, ReportRequestDto dto,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    reportService.reportComment(id, dto, memberDetails.getMember());
  }

  // api/report/thread?id=3
  @PostMapping("/thread")
  public void reportThread(@RequestParam Long id, ReportRequestDto dto,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    reportService.reportThread(id, dto, memberDetails.getMember());
  }


}
