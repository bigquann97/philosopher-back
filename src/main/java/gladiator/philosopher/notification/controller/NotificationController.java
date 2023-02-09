package gladiator.philosopher.notification.controller;

import gladiator.philosopher.notification.service.NotificationService;
import gladiator.philosopher.report.dto.NotificationResponseDto;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

  private final NotificationService notificationService;

  // 나에게 온 모든 알림 조회
  @GetMapping
  public List<NotificationResponseDto> getMyNotifications(
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return notificationService.getMyNotifications(memberDetails.getMember());
  }

}
