package gladiator.philosopher.notification.listener;

import gladiator.philosopher.notification.dto.NotificationRequestDto;
import gladiator.philosopher.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class NotificationListener {

  private final NotificationService notificationService;

  @TransactionalEventListener
  @Async
  public void handleNotification(NotificationRequestDto request) {
    notificationService.send(request);
  }

}