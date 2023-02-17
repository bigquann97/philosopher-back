package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.PostRecommend;
import java.util.List;

public interface NotificationService {

  void notifyToRecommendersThatThreadHasStarted(final Post post,
      final List<PostRecommend> recommends);

  List<NotificationResponseDto> getMyNotifications(final Account member);

}
