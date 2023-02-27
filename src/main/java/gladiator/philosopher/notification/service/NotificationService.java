package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Mention;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.notification.dto.NotificationRequestDto;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

  void notifyToRecommendersThatThreadHasStarted(Thread thread, List<PostRecommend> recommends);

  MyPage<NotificationResponseDto> getMyNotifications(int page, final Account member);

  void notifySomeoneMentionedYou(Mention mention);

  SseEmitter subscribe(Account account, String lastEventId);

  void send(NotificationRequestDto request);

}
