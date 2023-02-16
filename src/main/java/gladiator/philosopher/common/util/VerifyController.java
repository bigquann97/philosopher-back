package gladiator.philosopher.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class VerifyController {

  private final VerifyEmailService verifyEmailService;

  // 비밀번호 재설정 이메일 전송
  @PostMapping("/mail")
  @ResponseStatus(HttpStatus.OK)
  public void verify(@RequestParam String email) {
    verifyEmailService.sendVerificationMail(email);
  }

  // 인증링크 확인
  @PostMapping("/mail/{code}")
  @ResponseStatus(HttpStatus.OK)
  public void getVerify(@RequestParam String email, @PathVariable String code) {
    verifyEmailService.verifyEmail(email, code);
  }

}