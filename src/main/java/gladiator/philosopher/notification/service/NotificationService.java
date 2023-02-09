package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.dto.NotificationResponseDto;
import java.util.List;

public interface NotificationService {

  void notifyToRecommendersThatThreadHasStarted(Post post);

  List<NotificationResponseDto> getMyNotifications(Account member);
}
