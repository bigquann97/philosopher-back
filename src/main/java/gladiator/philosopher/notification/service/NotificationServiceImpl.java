package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.notification.entity.Notification;
import gladiator.philosopher.notification.repository.NotificationRepository;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  @Transactional
  public void notifyToRecommendersThatThreadHasStarted(final Post post, final Thread thread) {
    for (Recommend recommend : thread.getRecommends()) {
      Account user = recommend.getAccount();
      String content =
          user.getNickname() + "님이 추천을 누른 \"" + post.getTitle() + "\" 게시글에 대한 회의장이 열렸습니다!";
      Notification notification = Notification.builder().account(user).content(content).build();
      notificationRepository.save(notification);
    }
  }

  @Override
  public List<NotificationResponseDto> getMyNotifications(Account member) {
    List<Notification> notifications = notificationRepository.findByAccount(member);
    return notifications.stream().map(NotificationResponseDto::new)
        .collect(Collectors.toList());
  }


}
