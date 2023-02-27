package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.admin.dto.ModifyCommentStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyPostStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyThreadRequestDto;
import gladiator.philosopher.admin.dto.ModifyThreadStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.dto.CommentReportSearchCondition;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.PostReportSearchCondition;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.domain.Pageable;

public interface AdminService {

  void modifyUserRole(Account account);

  MyPage<UserInfoByAdminResponseDto> getAccounts(AccountSearchCondition condition,
      Pageable pageable);

  MyPage<PostReportResponseDto> getPostsReports(PostReportSearchCondition condition, Pageable pageable);

  MyPage<ThreadSimpleResponseDto> selectArchivedThreads(final ThreadSearchCond of);

  MyPage<ThreadsSimpleResponseDtoByAdmin> searchByThreadsAdmin(final ThreadSearchCondByAdmin cond,
      Pageable pageable);

  Long modifyThreadStatus(Thread thread, ModifyThreadStatusRequestDtoByAdmin requestDtoByAdmin);

  Long modifyPostCategory(Post post, Category category);

  MyPage<ThreadReportResponseDto> getThreadsReports(ThreadReportSearchCondition condition, Pageable pageable);

  MyPage<CommentReportResponseDto> getCommentsReports(CommentReportSearchCondition condition, Pageable pageable);

  Long modifyPostStatus(Post post, ModifyPostStatusRequestDtoByAdmin requestDtoByAdmin);

  Long modifyCommentStatus(Comment comment, ModifyCommentStatusRequestDtoByAdmin requestDtoByAdmin);
}
