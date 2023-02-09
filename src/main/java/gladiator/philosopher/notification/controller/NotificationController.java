package gladiator.philosopher.notification.controller;

import gladiator.philosopher.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

}
