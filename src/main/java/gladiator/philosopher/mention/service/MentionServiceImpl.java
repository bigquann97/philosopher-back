package gladiator.philosopher.mention.service;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.mention.entity.Mention;
import gladiator.philosopher.mention.repository.MentionRepository;
import java.util.ArrayList;
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
  private final CommentRepository commentRepository;

  // 댓글 내용 바탕으로 멘션 객체 생성
  @Override
  @Transactional
  public void mentionComment(final Comment mentioningComment) {
    List<Mention> mentions = extractMentions(mentioningComment);
    mentionRepository.saveAll(mentions);
  }

  @Override
  @Transactional
  public void deleteMentions(final Comment mentioningComment) {
    mentionRepository.deleteByMentioningComment(mentioningComment);
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

  private List<Mention> extractMentions(Comment mentioningComment) {
    List<Mention> mentions = new ArrayList<>();

    String content = mentioningComment.getContent().replaceAll(" ", ""); // 공백 제거

    while (true) {
      Long mentionedCommentId = parseFirstHashtagNum(content);

      if (mentionedCommentId == null) {
        break;
      }

      Comment mentionedComment = commentRepository.findById(mentionedCommentId)
          .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글"));
      Mention mention = Mention.builder()
          .mentionedComment(mentionedComment)
          .mentioningComment(mentioningComment)
          .build();
      mentions.add(mention);
      content = content.replaceAll("#" + mentionedCommentId, "");
    }

    return mentions;
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
