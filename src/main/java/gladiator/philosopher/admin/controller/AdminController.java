package gladiator.philosopher.admin.controller;

import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.entity.Report;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/accounts") // 모든 회원 정보 가지고 오기
  public ResponseEntity<List<UserInfoResponseDto>> getAccounts(){
    return ResponseEntity.ok(adminService.getUsersInfoList());
  }
  // 권한 관련 수정은 조금 더 방식을 생각해보자
  @PatchMapping("/role/{id}") // 권한 관련 수정 ( 미완성 )
  public void modifyUserRole(@PathVariable("id")Long id){
  }
  @DeleteMapping("post/{id}") // 게시글 삭제 (완료) -> 일단 데이터는 지우지말고, db만 날린 후에 작업 진행해봅시다. -> 그냥 cacade 조건 줘벌임
  public void deletePostByAdmin(@PathVariable("id")Long id){
    postService.deletePostByAdmin(id); // 바로 post로?  or admin -> post로 ?
  }
  @PatchMapping("post/{id}") // 게시글 수정
  public void modifyPostByAdmin(@PathVariable("id")Long id,@RequestBody PostRequestDto postRequestDto){
    postService.modifyPostByAdmin(id,postRequestDto);
  }
  @GetMapping("/reports") // 신고 목록 조회 ( 신고 목록 조회 관련해서 신고별로 entity를 분리할 것인지? 아니면 그냥 search 조건 부여해서? )
  public ResponseEntity<List<ReportResponseDto>> getReports(){
    return ResponseEntity.ok(adminService.getReports());
  }

  // 앞으로 더 필요한 로직
  // 댓글 삭제, 댓글 수정 -> 삭제시 연관데이터 고려해야할 것 ( 게시글 삭제도 마찬가지 -> 현재 500 error 발생함 )


}
