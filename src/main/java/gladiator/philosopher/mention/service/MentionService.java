package gladiator.philosopher.mention.service;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.mention.entity.Mention;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface MentionService {

  // 댓글 내용 바탕으로 멘션 객체 생성
  @Transactional
  void mentionComment(Comment mentioningComment);

  // 파라미터 댓글이 MentioningComment 인 모든 Mention 가져오기
  // 해당 댓글이 멘션중인 모든 댓글 가져오기
  @Transactional
  List<Mention> getMentioningList(Comment mentioningComment);

  // 파라미터 댓글이 MentionedComment 인 모든 Mention 가져오기
  // 해당 댓글을 멘션한 모든 댓글 가져오기
  @Transactional
  List<Mention> getMentionedList(Comment mentionedComment);
}
