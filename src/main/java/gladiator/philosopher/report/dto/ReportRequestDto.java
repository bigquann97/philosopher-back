package gladiator.philosopher.report.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.entity.CommentReport;
import gladiator.philosopher.report.entity.PostReport;
import gladiator.philosopher.report.entity.ReportCategory;
import gladiator.philosopher.report.entity.ThreadReport;
import gladiator.philosopher.thread.entity.Thread;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ReportRequestDto {

  private final String content;
  private final ReportCategory category;

  // dtoToEntity -> post
  public PostReport toEntity(Post post, Account reporter) {
    return PostReport.builder()
        .category(this.category)
        .content(this.content)
        .reporter(reporter)
        .reported(post.getAccount())
        .postId(post.getId())
        .build();
  }

  // dtoToEntity -> thread
  public ThreadReport toEntity(Thread thread, Account reporter) {
    return ThreadReport.builder()
        .category(this.category)
        .content(this.content)
        .reporter(reporter)
        .reported(thread.getAccount())
        .threadId(thread.getId())
        .build();
  }

  // dtoToEntity -> comment
  public CommentReport toEntity(Comment comment, Account reporter) {
    return CommentReport.builder()
        .category(this.category)
        .content(this.content)
        .reporter(reporter)
        .reported(comment.getAccount())
        .commentId(comment.getId())
        .build();
  }
}
