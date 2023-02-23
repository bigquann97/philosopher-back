package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.Mention;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.comment.repository.MentionRepository;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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
    if (!mentions.isEmpty()) {
      mentionRepository.saveAll(mentions);
    }
  }

  @Override
  @Transactional
  public void deleteMentions(final Comment mentioningComment) {
    mentionRepository.deleteByMentioningComment(mentioningComment);
    mentionRepository.flush();
  }

  private List<Mention> extractMentions(Comment mentioningComment) {
    Set<Long> mentionIds = parseAllHashtagNums(mentioningComment.getContent());
    List<Mention> mentions = new ArrayList<>();

    for (Long id : mentionIds) {
      Comment comment = commentRepository.findById(id).orElse(null);
      if (comment != null) {
        Mention mention = Mention.builder()
            .mentioningComment(mentioningComment)
            .mentionedComment(comment)
            .build();
        mentions.add(mention);
      }
    }
    return mentions;
  }

  private Set<Long> parseAllHashtagNums(String body) {
    Set<Long> hashtagNums = new LinkedHashSet<>();

    while (body.contains("#")) {
      Long num;
      int sharpIdx = body.indexOf("#");
      int pointer = sharpIdx + 1;

      while (pointer < body.length() && body.charAt(pointer) >= 48 && body.charAt(pointer) <= 57) {
        pointer++;
      }

      String numStr = body.substring(sharpIdx + 1, pointer);

      try {
        num = Long.parseLong(numStr);
      } catch (NumberFormatException e) {
        num = null;
      }

      if (num != null) {
        hashtagNums.add(num);
      }

      body = body.substring(pointer);
    }

    return hashtagNums;
  }

}
