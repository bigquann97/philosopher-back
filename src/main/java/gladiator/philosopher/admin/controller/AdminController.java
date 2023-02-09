package gladiator.philosopher.admin.controller;

import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import gladiator.philosopher.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  @PatchMapping("/role/{id}") // 권한 관련 수정
  public void modifyUserRole(@PathVariable("id")Long id){
  }

  @DeleteMapping("post/{id}")// post 삭제
  public void deletePostByAdmin(@PathVariable("id")Long id){
    postService.deletePostByAdmin(id);
  }

}
