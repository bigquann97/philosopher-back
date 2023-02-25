package gladiator.philosopher.notification.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.Mention;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.notification.dto.NotificationRequestDto;
import gladiator.philosopher.notification.dto.NotificationResponseDto;
import gladiator.philosopher.notification.entity.Notification;
import gladiator.philosopher.notification.repository.EmitterRepository;
import gladiator.philosopher.notification.repository.NotificationRepository;
import gladiator.philosopher.recommend.entity.PostRecommend;
import gladiator.philosopher.recommend.entity.Recommend;
import gladiator.philosopher.thread.entity.Thread;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final EmitterRepository emitterRepository;

  public static final String CLIENT_BASIC_URL = "https://localhost.com";
  private final Long timeout = 1000L * 60L * 60L * 24L * 14L;

  @Override
  @Transactional
  public void notifyToRecommendersThatThreadHasStarted(
      final Thread thread,
      final List<PostRecommend> recommends
  ) {
    for (Recommend recommend : recommends) {
      Account account = recommend.getAccount();

      String content =
          account.getNickname() + "님이 추천을 누른 \""
              + thread.getTitle() + "\" 게시글에 대한 회의장이 열렸습니다!";

      String redirectUrl = CLIENT_BASIC_URL + "/threads/" + thread.getId();

      NotificationRequestDto req = NotificationRequestDto.builder()
          .account(account)
          .content(content)
          .redirectUrl(redirectUrl)
          .build();

      send(req);
    }
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

    NotificationRequestDto req = NotificationRequestDto.builder()
        .account(mentionedAccount)
        .content(content)
        .redirectUrl(redirectUrl)
        .build();

    send(req);
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

  @Transactional
  public SseEmitter subscribe(Account account, String lastEventId) {
    String emitterId = makeTimeIncludeId(account);

    SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));

    emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
    emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

    String eventId = makeTimeIncludeId(account);
    sendDummyData(emitterId, emitter, eventId,
        "EventStream Created. [memberId=" + account.getId() + "]");

    if (hasLostData(lastEventId)) {
      Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(
          String.valueOf(account.getId()));
      events.entrySet().stream()
          .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
          .forEach(entry -> sendDummyData(emitterId, emitter, entry.getKey(), entry.getValue()));
    }
    return emitter;
  }


  @Override
  public void send(NotificationRequestDto request) {
    Notification notification = saveNotification(request);
    sendNotification(request, notification);
  }

  @Async
  public void sendNotification(NotificationRequestDto request, Notification notification) {
    String receiverId = String.valueOf(request.getAccount().getId());
    String eventId = receiverId + "_" + System.currentTimeMillis();
    Map<String, SseEmitter> emitters = emitterRepository
        .findAllEmitterStartWithByAccountId(receiverId);
    emitters.forEach(
        (key, emitter) -> {
          emitterRepository.saveEventCache(key, notification);
          sendDummyData(key, emitter, eventId, NotificationResponseDto.of(notification));
        }
    );
  }

  @Transactional
  public Notification saveNotification(NotificationRequestDto request) {
    Notification notification = Notification.builder()
        .content(request.getContent())
        .redirectUrl(request.getRedirectUrl())
        .isRead(false)
        .account(request.getAccount())
        .build();
    notificationRepository.save(notification);
    return notification;
  }

  private String makeTimeIncludeId(Account account) {
    return account.getId() + "_" + System.currentTimeMillis();
  }

  private void sendDummyData(String emitterId, SseEmitter emitter, String eventId, Object data) {
    try {
      emitter.send(SseEmitter.event()
          .id(eventId)
          .data(data));
    } catch (IOException exception) {
      emitterRepository.deleteById(emitterId);
    }
  }

  private boolean hasLostData(String lastEventId) {
    return !lastEventId.isEmpty();
  }

}
