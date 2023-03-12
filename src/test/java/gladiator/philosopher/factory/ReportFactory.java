package gladiator.philosopher.factory;

import gladiator.philosopher.report.entity.CommentReport;
import gladiator.philosopher.report.entity.PostReport;
import gladiator.philosopher.report.enums.ReportCategory;
import gladiator.philosopher.report.entity.ThreadReport;

public class ReportFactory {

  public static PostReport createPostReport1() {
    return PostReport.builder()
        .postId(1L)
        .reported(AccountFactory.createUserAccount1())
        .reporter(AccountFactory.createUserAccount2())
        .content("content")
        .category(ReportCategory.ABUSE)
        .build();
  }

  public static PostReport createPostReport2() {
    return PostReport.builder()
        .postId(1L)
        .reported(AccountFactory.createUserAccount2())
        .reporter(AccountFactory.createUserAccount1())
        .content("content")
        .category(ReportCategory.ADVERTISEMENT)
        .build();
  }

  public static ThreadReport createThreadReport1() {
    return ThreadReport.builder()
        .threadId(1L)
        .reported(AccountFactory.createUserAccount1())
        .reporter(AccountFactory.createUserAccount2())
        .content("content")
        .category(ReportCategory.ABUSE)
        .build();
  }

  public static ThreadReport createThreadReport2() {
    return ThreadReport.builder()
        .threadId(1L)
        .reported(AccountFactory.createUserAccount2())
        .reporter(AccountFactory.createUserAccount1())
        .content("content")
        .category(ReportCategory.ADVERTISEMENT)
        .build();
  }

  public static CommentReport createCommentReport1() {
    return CommentReport.builder()
        .commentId(1L)
        .reported(AccountFactory.createUserAccount1())
        .reporter(AccountFactory.createUserAccount2())
        .content("content")
        .category(ReportCategory.ABUSE)
        .build();
  }

  public static CommentReport createCommentReport2() {
    return CommentReport.builder()
        .commentId(1L)
        .reported(AccountFactory.createUserAccount2())
        .reporter(AccountFactory.createUserAccount1())
        .content("content")
        .category(ReportCategory.IRRELEVANT)
        .build();
  }

}
