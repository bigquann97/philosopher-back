package gladiator.philosopher.report.repository;

import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.entity.Report;
import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ReportRepository extends JpaRepository<Report, Long> {

  long countByThread(@NonNull Thread thread);

  long countByPost(@NonNull Post post);

  long countByComment(@NonNull Comment comment);

}
