package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.Mention;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface MentionRepository extends JpaRepository<Mention, Long> {

  void deleteByMentionedComment(@NonNull Comment mentionedComment);

  void deleteByMentioningComment(@NonNull Comment mentioningComment);

  List<Mention> findByMentionedComment(@NonNull Comment mentionedComment);

  List<Mention> findByMentioningComment(@NonNull Comment mentioningComment);

}
