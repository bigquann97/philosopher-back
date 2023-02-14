package gladiator.philosopher.notification.dto;

import gladiator.philosopher.notification.entity.Notification;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class NotificationResponseDto {

  private final String message;

  private final LocalDateTime createdDate;

  @Builder
  public NotificationResponseDto(final String message, final LocalDateTime createdDate) {
    this.message = message;
    this.createdDate = createdDate;
  }

  public static NotificationResponseDto of(final Notification notification) {
    return NotificationResponseDto.builder()
        .message(notification.getContent())
        .createdDate(notification.getCreatedDate())
        .build();
  }

}
