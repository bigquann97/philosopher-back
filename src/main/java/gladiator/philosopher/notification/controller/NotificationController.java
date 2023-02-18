package gladiator.philosopher.notification.controller;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

  private final NotificationService notificationService;

  // 나에게 온 모든 알림 조회
  @GetMapping
  public MyPage<NotificationResponseDto> getMyNotifications(
      final @DefaultValue("0") int page,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    return notificationService.getMyNotifications(page, accountDetails.getAccount());
  }

}
