package gladiator.philosopher.mention.service;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.mention.entity.Mention;
import java.util.List;

public interface MentionService {

  void mentionComment(Comment mentioningComment);

  void deleteMentions(Comment mentioningComment);

  List<Mention> getMentioningList(Comment mentioningComment);

  List<Mention> getMentionedList(Comment mentionedComment);
}
