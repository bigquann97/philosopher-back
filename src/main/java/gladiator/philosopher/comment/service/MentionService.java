package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.entity.Comment;

public interface MentionService {

  void mentionComment(Comment mentioningComment);

  void deleteMentions(Comment mentioningComment);

}
