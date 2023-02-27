package gladiator.philosopher.admin.controller;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ModifyCategoryRequestDtoByAdmin;
import gladiator.philosopher.admin.dto.ModifyThreadRequestDto;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.service.CategoryService;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.entity.PageRequest;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.enums.Sort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  /**
   * 회원 정보 가지고 오기
   *
   * @param condition
   * @param pageRequest
   * @return
   */
  @GetMapping("/accounts")
  public MyPage<UserInfoByAdminResponseDto> searchAccounts
  (
      final AccountSearchCondition condition,
      final PageRequest pageRequest) {
    Pageable pageable = pageRequest.of();
    return adminService.getAccounts(condition, pageable);
  }

  /**
   * 권한 수정
   *
   * @param id
   */
  @PatchMapping("/modify/account/role/{id}")
  @PreAuthorize("hasRole('ROLE_MASTER')")
  @ResponseStatus(HttpStatus.CREATED)
  public void modifyUserRole(final @PathVariable("id") Long id) {
    adminService.modifyUserRole(accountService.getAccount(id));
  }

  /**
   * 게시글 삭제 -> 상태만 DELETE로 바꿈
   *
   * @param id
   */
  @DeleteMapping("post/{id}")
  public void deletePostByAdmin(final @PathVariable("id") Long id) {
    postService.deletePostByAdmin(id);
  }

  /**
   * 게시글 수정
   *
   * @param id
   * @param postRequestDto
   */
  @PatchMapping("post/{id}")
  public void modifyPostByAdmin(
      final @PathVariable("id") Long id,
      final @RequestBody PostRequestDto postRequestDto
  ) {
    postService.modifyPostByAdmin(id, postRequestDto);
  }

  /**
   * 신고 목록 조회 (Post)
   *
   * @return
   */
  @GetMapping("/reports/posts")
  public List<PostReportResponseDto> getPostsReports() {
    return adminService.getPostsReports();
  }

  /**
   * 댓글 수정
   */
  @PatchMapping("comment/{id}")
  public void modifyCommentByAdmin(
      final @PathVariable("id") Long id,
      final @RequestBody CommentRequestDto commentRequestDto
  ) {
    commentService.modifyCommentByAdmin(id, commentRequestDto);
  }

  /**
   * 댓글 삭제 -> 댓글 상태만 DELETE로 바꿈
   */
  @DeleteMapping("comment/{id}")
  public void deleteCommentByAdmin(final @PathVariable("id") Long id) {
    commentService.deleteCommentByAdmin(id);
  }


  /**
   * 아카이브 된 쓰레드 조회 by 관호님
   *
   * @param
   * @return
   */
  @GetMapping("/thread/archived")
  @ResponseStatus(HttpStatus.OK)
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
  public MyPage<ThreadsSimpleResponseDtoByAdmin> searchThreads(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final ThreadSearchCondByAdmin cond,
      final Pageable pageable) {
    return adminService.searchByThreadsAdmin(cond, pageable);
  }


  /**
   * 쓰레드 수정
   *
   * @param accountDetails
   * @param id
   * @param threadRequestDto
   * @return
   */
  @PutMapping("thread/{id}")
  public Long modifyThread(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final @PathVariable("id") Long id,
      final @RequestBody ModifyThreadRequestDto threadRequestDto
  ) {
    Long threadId = adminService.modifyThread(id, threadRequestDto);
    return threadId;
  }

  /**
   * 게시글 카테고리 수정
   * @param accountDetails
   * @param id
   * @param requestDtoByAdmin
   * @return
   */
  @PatchMapping("category/{id}")
  public Long modifyPostCategory(
      final @AuthenticationPrincipal AccountDetails accountDetails,
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
