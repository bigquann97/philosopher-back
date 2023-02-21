package gladiator.philosopher.notification.dto;

import gladiator.philosopher.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationResponseDto {

  private final String message;

  private final String redirectUrl;

  @Builder
  public NotificationResponseDto(final String message, final String redirectUrl) {
    this.redirectUrl = redirectUrl;
    this.message = message;
  }

  public static NotificationResponseDto of(final Notification notification) {
    return NotificationResponseDto.builder()
        .message(notification.getContent())
        .redirectUrl(notification.getRedirectUrl())
        .build();
  }

}
