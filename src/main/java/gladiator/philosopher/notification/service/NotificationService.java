package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;

public interface NotificationService {

  void notifyToRecommendersThatThreadHasStarted(Thread thread, List<PostRecommend> recommends);

  MyPage<NotificationResponseDto> getMyNotifications(int page, final Account member);

}
