package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.List;

public interface NotificationService {

  void notifyToRecommendersThatThreadHasStarted(Post post, List<Recommend> recommends);

  List<NotificationResponseDto> getMyNotifications(Account member);

}
