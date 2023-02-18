package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.notification.entity.Notification;
import gladiator.philosopher.notification.repository.NotificationRepository;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  @Transactional
  public void notifyToRecommendersThatThreadHasStarted(
      final Post post,
      final List<PostRecommend> recommends
  ) {
    for (Recommend recommend : recommends) {
      Account user = recommend.getAccount();

      String content =
          user.getNickname() + "님이 추천을 누른 \""
              + post.getTitle() + "\" 게시글에 대한 회의장이 열렸습니다!";

      Notification notification = Notification.builder().account(user).content(content).build();

      notificationRepository.save(notification);
    }
  }

  @Override
  public MyPage<NotificationResponseDto> getMyNotifications(int page, final Account account) {
    PageRequest pageRequest = PageRequest.of(page, 5);
    Page<Notification> paged = notificationRepository.findByAccountOrderByIdDesc(account,
        pageRequest);

    List<NotificationResponseDto> dtos = paged.getContent().stream()
        .map(NotificationResponseDto::of).collect(Collectors.toList());

    return new MyPage<>(new PageImpl<>(dtos, paged.getPageable(), paged.getTotalElements()));
  }

}
