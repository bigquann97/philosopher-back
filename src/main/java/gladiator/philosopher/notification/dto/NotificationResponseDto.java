package gladiator.philosopher.notification.dto;

import gladiator.philosopher.notification.entity.Notification;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class NotificationResponseDto {

  private final String message;

  private final LocalDateTime createdAt;

  @Builder
  public NotificationResponseDto(Notification notification) {
    this.message = notification.getContent();
    this.createdAt = notification.getCreatedDate();
  }

}
