package gladiator.philosopher.report.dto;

import gladiator.philosopher.Account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.entity.Report;
import gladiator.philosopher.report.entity.ReportCategory;
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
  public Report toEntity(Post post, Account reporter) {
    return Report.builder()
        .category(this.category)
        .content(this.content)
        .reporter(reporter)
        .reported(post.getAccount())
        .postId(post.getId())
        .commentId(null)
        .threadId(null)
        .build();
  }

  // dtoToEntity -> thread
  public Report toEntity(Thread thread, Account reporter) {
    return Report.builder()
        .category(this.category)
        .content(this.content)
        .reporter(reporter)
        .reported(thread.getAccount())
        .postId(null)
        .commentId(null)
        .threadId(thread.getId())
        .build();
  }

  // dtoToEntity -> comment
  public Report toEntity(Comment comment, Account reporter) {
    return Report.builder()
        .category(this.category)
        .content(this.content)
        .reporter(reporter)
        .reported(comment.getAccount())
        .postId(null)
        .commentId(comment.getId())
        .threadId(null)
        .build();
  }
}
