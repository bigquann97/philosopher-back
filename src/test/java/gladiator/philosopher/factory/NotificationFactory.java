package gladiator.philosopher.factory;

import gladiator.philosopher.notification.entity.Notification;

public class NotificationFactory {

  public static Notification createNotification1() {
    return Notification.builder()
        .redirectUrl("redirectUrl")
        .account(AccountFactory.createUserAccount1())
        .content("notification content")
        .build();
  }

  public static Notification createNotification2() {
    return Notification.builder()
        .redirectUrl("redirectUrl2")
        .account(AccountFactory.createUserAccount1())
        .content("notification content2")
        .build();
  }

  public static Notification createNotification3() {
    return Notification.builder()
        .redirectUrl("redirectUrl3")
        .account(AccountFactory.createUserAccount1())
        .content("notification content3")
        .build();
  }

}
