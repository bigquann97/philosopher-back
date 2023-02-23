package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.Mention;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.notification.entity.Notification;
import gladiator.philosopher.notification.repository.NotificationRepository;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.thread.entity.Thread;
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

  public static final String CLIENT_BASIC_URL = "https://localhost.com";

  @Override
  @Transactional
  public void notifyToRecommendersThatThreadHasStarted(
      final Thread thread,
      final List<PostRecommend> recommends
  ) {
    for (Recommend recommend : recommends) {
      Account user = recommend.getAccount();

      String content =
          user.getNickname() + "님이 추천을 누른 \""
              + thread.getTitle() + "\" 게시글에 대한 회의장이 열렸습니다!";

      String redirectUrl = CLIENT_BASIC_URL + "/threads/" + thread.getId();

      Notification notification =
          Notification.builder()
              .account(user)
              .content(content)
              .redirectUrl(redirectUrl)
              .build();

      notificationRepository.save(notification);
    }
  }

  @Override
  public MyPage<NotificationResponseDto> getMyNotifications(final int page, final Account account) {
    PageRequest pageRequest = PageRequest.of(page, 5);
    Page<Notification> paged = notificationRepository.findByAccountOrderByIdDesc(account,
        pageRequest);

    List<NotificationResponseDto> dtos = paged.getContent().stream()
        .map(NotificationResponseDto::of).collect(Collectors.toList());

    return new MyPage<>(new PageImpl<>(dtos, paged.getPageable(), paged.getTotalElements()));
  }

  @Override
  public void notifySomeoneMentionedYou(Mention mention) {
    Thread thread = mention.getMentioningComment().getThread();
    String threadTitle = thread.getTitle();
    Long threadId = thread.getId();

    Comment mentioningComment = mention.getMentioningComment();
    Account mentioningAccount = mentioningComment.getAccount();
    Long mentioningCommentId = mentioningComment.getId();

    Comment mentionedComment = mention.getMentionedComment();
    Account mentionedAccount = mentionedComment.getAccount();
    Long mentionedCommentId = mentionedComment.getId();

    if (mentionedAccount.equals(mentioningAccount)) { // self mention 한 경우 알람 없음
      return;
    }

    String content = "\"" + threadTitle + "\""
        + " 토론장에서 " + mentioningAccount.getNickname() + " 님의 "
        + mentioningCommentId + "번 댓글이 "
        + "당신의 " + mentionedCommentId + "번 댓글을 언급했습니다!";

    String redirectUrl = CLIENT_BASIC_URL + "/threads/" + threadId;

    Notification notification = Notification.builder()
        .content(content)
        .redirectUrl(redirectUrl)
        .account(mentionedAccount)
        .build();

    notificationRepository.save(notification);
  }

}
