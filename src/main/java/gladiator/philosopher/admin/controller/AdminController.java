package gladiator.philosopher.admin.controller;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.dto.post.PostReportResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Sort;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final ThreadRepository threadRepository;

  /**
   * 회원 정보 가지고 오기
   * @param condition
   * @param pageable
   * @return
   */
  @GetMapping("/accountsV2")
  public MyPage<UserInfoByAdminResponseDto> searchAccounts(final AccountSearchCondition condition, Pageable pageable) {
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
   * 신고 목록 조회 ( 페이징 처리 해야함)
   *
   * @return
   */
  @GetMapping("/reports")
  public List<ReportResponseDto> getReports() {
    return adminService.getReports();
  }

  /**
   * 신고 목록 조회 (Post)
   * @return
   */
  // 신고목록 관련해서 각자 신고 목록 조회를 체크할 수 있도록 해야한다 -> ( 3개 ) -> 이 부분에 관해서 querydsl을 적용하는것이 맞을까?
  @GetMapping("/reports/posts")
  public List<PostReportResponseDto> getPostsReports(){
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
   * 쓰레드 수정, 삭제
   */


  /**
   * 쓰레드 목록 조회
   */

  /**
   * 아카이브 된 쓰레드 조회 by 관호님
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
  ){
    return adminService.selectArchivedThreads(ThreadSearchCond.of(page, sort, word, category));
  }


  @GetMapping("thread/test")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<ThreadsSimpleResponseDtoByAdmin> searchThreads(
      final ThreadSearchCondByAdmin cond,
      final Pageable pageable){
    return adminService.searchByThreadsAdmin(cond,pageable);
  }



}
