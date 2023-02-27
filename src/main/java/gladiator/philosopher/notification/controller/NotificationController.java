package gladiator.philosopher.notification.controller;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.notification.service.NotificationService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public SseEmitter subscribe(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId,
      final HttpServletResponse response
  ) {
    response.setHeader("Connection", "keep-alive");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("X-Accel-Buffering", "no");
    return notificationService.subscribe(accountDetails.getAccount(), lastEventId);
  }

  // 나에게 온 모든 알림 조회
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public MyPage<NotificationResponseDto> getMyNotifications(
      final @RequestParam(required = false, defaultValue = "1") int page,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    int newPage = page <= 0 ? 0 : page - 1;
    return notificationService.getMyNotifications(newPage, accountDetails.getAccount());
  }

}
