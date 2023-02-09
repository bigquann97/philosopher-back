package gladiator.philosopher.admin.controller;

import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.admin.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

  private final AdminService adminService;

  @GetMapping("/accounts") // 모든 회원 정보 가지고 오기
  public ResponseEntity<List<UserInfoResponseDto>> getAccounts(){
    return ResponseEntity.ok(adminService.getUsersInfoList());
  }

}
