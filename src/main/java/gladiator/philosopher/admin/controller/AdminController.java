package gladiator.philosopher.admin.controller;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ModifyCategoryRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyCommentStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyPostStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyThreadStatusRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.service.CategoryService;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.entity.PageRequest;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
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
import gladiator.philosopher.thread.enums.Sort;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;
  private final PostService postService;
  private final AccountService accountService;
  private final CommentService commentService;
  private final CategoryService categoryService;
  private final ThreadService threadService;

  // 정보 검색
  /**
   * 회원 정보 가지고 오기
   *
   * @param condition
   * @param pageRequest
   * @return
   */
  @GetMapping("/accounts")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public MyPage<UserInfoByAdminResponseDto> searchAccounts(
      final AccountSearchCondition condition,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return adminService.getAccounts(condition, pageable);
  }

  /**
   * 신고 목록 조회 ( Post )
   *
   * @return
   */
  @GetMapping("/reports/posts")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public MyPage<PostReportResponseDto> getPostsReports(
      final PostReportSearchCondition condition,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return adminService.getPostsReports(condition, pageable);
  }

  /**
   * 신고 목록 조회 ( Thread )
   *
   * @return
   */
  @GetMapping("/reports/threads")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public MyPage<ThreadReportResponseDto> getThreadsReports(
      final ThreadReportSearchCondition condition,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return adminService.getThreadsReports(condition, pageable);
  }

  /**
   * 신고 목록 조회 (comment)
   *
   * @return
   */
  @GetMapping("/reports/comments")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public MyPage<CommentReportResponseDto> getCommentsReports(
      final CommentReportSearchCondition condition,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return adminService.getCommentsReports(condition, pageable);
  }

  /**
   * 아카이브 된 쓰레드 조회 by 관호님
   *
   * @param
   * @return
   */
  @GetMapping("/thread/archived")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public MyPage<ThreadSimpleResponseDto> selectArchivedThreads(
      final @RequestParam(required = false) Long category,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Sort sort,
      final @RequestParam(required = false) String word
  ) {
    return adminService.selectArchivedThreads(ThreadSearchCond.of(page, sort, word, category));
  }

  /**
   * 쓰레드 간편 조회 ( 상태를 기준으로 조회 )
   *
   * @param cond
   * @param pageable
   * @return
   */
  @GetMapping("thread")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public MyPage<ThreadsSimpleResponseDtoByAdmin> searchThreads(
      final ThreadSearchCondByAdmin cond,
      final Pageable pageable
  ) {
    return adminService.searchByThreadsAdmin(cond, pageable);
  }

  // 정보 수정

  /**
   * 권한 수정
   *
   * @param id
   */
  @PatchMapping("/modify/account/role/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ResponseStatus(HttpStatus.CREATED)
  public void modifyUserRole(final @PathVariable("id") Long id) {
    adminService.modifyUserRole(accountService.getAccount(id));
  }

  /**
   * 게시글 상태 변경 -> 신고 접수 된 게시글에 대해서 삭제 (delete 상태) 하거나, 상태를 변경(blind)
   *
   * @param id
   * @param requestDtoByAdmin
   * @return
   */
  @PatchMapping("/post/{id}")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public Long modifyPostStatus(
      final @PathVariable("id") Long id,
      final @RequestBody ModifyPostStatusRequestDtoByAdmin requestDtoByAdmin
  ) {
    Post post = postService.getPostEntity(id);
    return adminService.modifyPostStatus(post, requestDtoByAdmin);
  }

  /**
   * 댓글 상태 변경 -> 신고 접수 된 게시글에 대해서 삭제 (delete 상태) 하거나, 상태를 변경(blind)
   *
   * @param id
   * @param requestDtoByAdmin
   * @return
   */
  @PatchMapping("comment/{id}")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public Long modifyCommentStatus(
      final @PathVariable("id") Long id,
      final @RequestBody ModifyCommentStatusRequestDtoByAdmin requestDtoByAdmin
  ) {
    Comment comment = commentService.getCommentEntity(id);
    return adminService.modifyCommentStatus(comment, requestDtoByAdmin);
  }


  /**
   * 쓰레드 상태 변경 -> 신고 접수 된 게시글에 대해서 삭제 (delete 상태) 하거나, 상태를 변경(blind)
   *
   * @param id
   * @param requestDtoByAdmin
   * @return
   */
  @PutMapping("thread/{id}")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public Long modifyThreadStatus(
      final @PathVariable("id") Long id,
      final @RequestBody ModifyThreadStatusRequestDtoByAdmin requestDtoByAdmin
  ) {
    Thread thread = threadService.getThreadEntity(id);
    return adminService.modifyThreadStatus(thread, requestDtoByAdmin);
  }


  /**
   * 게시글 카테고리 수정
   *
   * @param id
   * @param requestDtoByAdmin
   * @return
   */
  @PatchMapping("/category/{id}")
  @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public Long modifyPostCategory(
      final @PathVariable("id") Long id,
      final @RequestBody ModifyCategoryRequestDtoByAdmin requestDtoByAdmin
  ) {
    Category newCategory = categoryService.getCategoryEntity(
        requestDtoByAdmin.getCategoryId());
    Post post = postService.getPostEntity(id);
    Long postId = adminService.modifyPostCategory(post, newCategory);
    return postId;
  }

}
