package gladiator.philosopher.notification.dto;

import gladiator.philosopher.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationResponseDto {

  private final Long id;

  private final String message;

  private final String redirectUrl;

  @Builder
  public NotificationResponseDto(final Long id, final String message, final String redirectUrl) {
    this.id = id;
    this.redirectUrl = redirectUrl;
    this.message = message;
  }

  public static NotificationResponseDto of(final Notification notification) {
    return NotificationResponseDto.builder()
        .id(notification.getId())
        .message(notification.getContent())
        .redirectUrl(notification.getRedirectUrl())
        .build();
  }

}
