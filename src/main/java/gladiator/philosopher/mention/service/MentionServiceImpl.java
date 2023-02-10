package gladiator.philosopher.mention.service;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.mention.entity.Mention;
import gladiator.philosopher.mention.repository.MentionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {

  private final MentionRepository mentionRepository;
  private final CommentService commentService;

  // 댓글 내용 바탕으로 멘션 객체 생성
  @Override
  @Transactional
  public void mentionComment(Comment mentioningComment) {
    String content = mentioningComment.getContent().replaceAll(" ", ""); // 공백 제거
    Long mentionedCommentId = parseFirstHashtagNum(content);

    while (mentionedCommentId != null) {
      Comment mentionedComment = commentService.getCommentEntity(mentionedCommentId);
      Mention mention = Mention.builder()
          .mentionedComment(mentionedComment)
          .mentioningComment(mentioningComment)
          .build();
      mentionRepository.save(mention);
      content = content.replaceAll("#" + mentionedCommentId, "");
      mentionedCommentId = parseFirstHashtagNum(content);
    }
  }

  // 파라미터 댓글이 MentioningComment 인 모든 Mention 가져오기
  // 해당 댓글이 멘션중인 모든 댓글 가져오기
  @Override
  @Transactional
  public List<Mention> getMentioningList(Comment mentioningComment) {
    return mentionRepository.findByMentioningComment(mentioningComment);
  }

  // 파라미터 댓글이 MentionedComment 인 모든 Mention 가져오기
  // 해당 댓글을 멘션한 모든 댓글 가져오기
  @Override
  @Transactional
  public List<Mention> getMentionedList(Comment mentionedComment) {
    return mentionRepository.findByMentionedComment(mentionedComment);
  }

  private Long parseFirstHashtagNum(String str) {
    int firstSharpIdx = str.indexOf("#");
    int pointer = firstSharpIdx + 1;

    while (str.charAt(pointer) >= 48 && str.charAt(pointer) <= 57) {
      pointer++;
    }

    String num = str.substring(firstSharpIdx + 1, pointer);

    try {
      return Long.parseLong(num);
    } catch (NumberFormatException e) {
      return null;
    }
  }

}
