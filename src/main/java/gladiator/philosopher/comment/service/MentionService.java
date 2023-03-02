package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.entity.Comment;

public interface MentionService {

  void mentionComment(final Comment mentioningComment);

  void deleteMentions(final Comment mentioningComment);

}
