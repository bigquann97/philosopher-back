package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ModifyCommentStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyPostStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyThreadStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.dto.CommentReportSearchCondition;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.PostReportSearchCondition;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import gladiator.philosopher.report.service.ReportService;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.repository.ThreadRepository;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

  private final ThreadRepository threadRepository;
  private final CommentRepository commentRepository;
  private final ReportService reportService;
  private final AccountService accountService;
  private final ThreadService threadService;
  private final AccountRepository accountRepository;
  private final PostRepository postRepository;

  @Override
  public MyPage<UserInfoByAdminResponseDto> getAccounts(
      final AccountSearchCondition condition,
      final Pageable pageable
  ) {
    return accountService.searchAccounts(condition, pageable);
  }

  @Override
  public void modifyUserRole(final Account account) {
    accountService.updateAccountRole(account);
  }


  @Override
  public MyPage<ThreadSimpleResponseDto> selectArchivedThreads(final ThreadSearchCond of) {
    return threadService.selectArchivedThreads(of);
  }

  @Override
  public MyPage<PostReportResponseDto> getPostsReports(
      final PostReportSearchCondition condition,
      final Pageable pageable
  ) {
    return reportService.getPostReports(condition, pageable);
  }

  @Override
  public MyPage<ThreadReportResponseDto> getThreadsReports(
      final ThreadReportSearchCondition condition,
      final Pageable pageable
  ) {
    return reportService.getThreadReports(condition, pageable);
  }

  @Override
  public MyPage<CommentReportResponseDto> getCommentsReports(
      final CommentReportSearchCondition condition,
      final Pageable pageable) {
    return reportService.getCommentReports(condition, pageable);
  }

  @Override
  public MyPage<ThreadsSimpleResponseDtoByAdmin> searchByThreadsAdmin(
      final ThreadSearchCondByAdmin cond,
      final Pageable pageable
  ) {
    return threadService.searchThreadByAdmin(cond, pageable);
  }

  @Override
  public Long modifyThreadStatus(
      final Thread thread,
      final ModifyThreadStatusRequestDtoByAdmin requestDtoByAdmin
  ) {
    Thread resultThread = thread.modifyThreadStatusByAdmin(requestDtoByAdmin.getThreadStatus());
    threadRepository.saveAndFlush(resultThread);
    return resultThread.getId();
  }

  @Override
  public Long modifyPostCategory(
      final Post post,
      final Category category
  ) {
    log.info("변경전 post의 category는 :" + post.getCategory().getName());
    final Post updatePost = post.modifyCategory(category);
    postRepository.saveAndFlush(updatePost);
    log.info("변경후 post의 category는 :" + post.getCategory().getName());
    return updatePost.getId();
  }

  @Override
  public Long modifyPostStatus(
      final Post post,
      final ModifyPostStatusRequestDtoByAdmin requestDtoByAdmin) {
    Post resultPost = post.ModifyStatusByAdmin(requestDtoByAdmin.getPostStatus());
    postRepository.saveAndFlush(resultPost);
    return resultPost.getId();
  }

  @Override
  public Long modifyCommentStatus(
      final Comment comment,
      final ModifyCommentStatusRequestDtoByAdmin requestDtoByAdmin) {
    Comment resultComment = comment.modifyStatusByAdmin(requestDtoByAdmin.getCommentStatus());
    commentRepository.saveAndFlush(resultComment);
    return resultComment.getId();
  }

}
