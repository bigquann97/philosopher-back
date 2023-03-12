package gladiator.philosopher.report.controller;

import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
public class ReportController {

  private final ReportService reportService;

  /**
   * 게시글 신고
   * @param id
   * @param dto
   * @param accountDetails
   */
  @PostMapping("/post")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void reportPost(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    reportService.reportPost(id, dto, accountDetails.getAccount());
  }

  /**
   * 댓글 신고
   * @param id
   * @param dto
   * @param accountDetails
   */
  @PostMapping("/comment")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void reportComment(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    reportService.reportComment(id, dto, accountDetails.getAccount());
  }

  /**
   * 쓰레드 신고
   * @param id
   * @param dto
   * @param accountDetails
   */
  @PostMapping("/thread")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void reportThread(
      final @RequestParam Long id,
      final @RequestBody ReportRequestDto dto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    reportService.reportThread(id, dto, accountDetails.getAccount());
  }

}
