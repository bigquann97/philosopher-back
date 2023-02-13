package gladiator.philosopher.admin.controller;

import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;
  private final PostService postService;
  private final AccountService accountService;
  private final CommentService commentService;

  /**
   * 유저 목록 조회
   *
   * @return
   */
  @GetMapping("/accounts")
  public List<UserInfoResponseDto> getAccounts() {
    return adminService.getUsersInfoList();
  }

  /**
   * 권한 수정
   *
   * @param id
   */
  @PatchMapping("/modify/account/role/{id}")
  @PreAuthorize("hasRole('ROLE_MASTER')")
  public void modifyUserRole(@PathVariable("id") Long id) {
    adminService.modifyUserRole(accountService.getAccount(id));
  }

  /**
   * 게시글 삭제
   *
   * @param id
   */
  @DeleteMapping("post/{id}")
  public void deletePostByAdmin(@PathVariable("id") Long id) {
    postService.deletePostByAdmin(id);
  }

  /**
   * 게시글 수정
   *
   * @param id
   * @param postRequestDto
   */
  @PatchMapping("post/{id}")
  public void modifyPostByAdmin(@PathVariable("id") Long id,
      @RequestBody PostRequestDto postRequestDto) {
    postService.modifyPostByAdmin(id, postRequestDto);
  }

  /**
   * 신고 목록 조회
   *
   * @return
   */
  @GetMapping("/reports")
  public List<ReportResponseDto> getReports() {
    return adminService.getReports();
  }
  /**
   * 댓글 수정
   */
  @PatchMapping("comment/{id}")
  public void modifyCommentByAdmin(@PathVariable("id")Long id, @RequestBody CommentRequestDto commentRequestDto){
    commentService.modifyCommentByAdmin(id, commentRequestDto);
  }

  /**
   * 댓글 삭제
   */
  @DeleteMapping("comment/{id}")
  public void deleteCommentByAdmin(@PathVariable("id")Long id){
    commentService.deleteCommentByAdmin(id);
  }

  /**
   * 쓰레드 목록 조회
   */
  @GetMapping("/threadsV2")
  public ResponseEntity<List<ThreadsSimpleResponseDtoByAdmin>> getThreadsV2(){
    return ResponseEntity.status(200).body(adminService.getThreadsV2());
  }


  /**
   * 아카이브 목록 조회
   */


}
