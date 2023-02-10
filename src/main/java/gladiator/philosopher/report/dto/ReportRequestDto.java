package gladiator.philosopher.report.dto;

import gladiator.philosopher.account.entity.Account;
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
  public Report toEntity(Post post,Account reporter){
    return Report.postReport()
        .reporter(reporter)
        .reported(post.getAccount())
        .category(this.getCategory())
        .content(this.content)
        .post(post)
        .build();
  }

  // dtoToEntity -> thread
  public Report toEntity(Thread thread, Account reporter){
    return Report.threadReport()
        .reporter(reporter)
        .reported(thread.getAccount())
        .category(this.getCategory())
        .content(this.content)
        .thread(thread)
        .build();
  }
  // dtoToEntity -> comment
  public Report toEntity(Comment comment, Account reporter){
    return Report.commentReport()
        .reporter(reporter)
        .reported(comment.getAccount())
        .category(this.getCategory())
        .content(this.content)
        .comment(comment)
        .build();
  }
}
