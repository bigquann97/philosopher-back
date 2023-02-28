package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.Mention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface MentionRepository extends JpaRepository<Mention, Long> {

  void deleteByMentionedComment(@NonNull Comment mentionedComment);

  void deleteByMentioningComment(@NonNull Comment mentioningComment);

}
