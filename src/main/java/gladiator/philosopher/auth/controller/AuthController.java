package gladiator.philosopher.auth.controller;

import gladiator.philosopher.account.dto.login.SignInRequestDto;
import gladiator.philosopher.account.dto.login.SignInResponseDto;
import gladiator.philosopher.account.dto.login.SignUpRequestDto;
import gladiator.philosopher.auth.service.AuthService;
import gladiator.philosopher.common.jwt.TokenRequestDto;
import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final S3Uploader s3Uploader;
  private final String dirName = "AccountImg";

  /**
   * 회원가입
   */
  @PostMapping(
      value = "/sign-up",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(
      final @RequestPart("image") MultipartFile multipartFiles,
      final @Valid @RequestPart("dto") SignUpRequestDto signUpRequestDto
  ) {
      s3Uploader.checkFileExtension(multipartFiles);
      final String imageUrl = s3Uploader.upLoadFileToSingle(multipartFiles, dirName);
      authService.signUp(imageUrl, signUpRequestDto);
  }

  /**
   * 로그인
   */
  @PostMapping("/sign-in")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto login(
      final @RequestBody SignInRequestDto signInRequestDto,
      final HttpServletResponse response
  ) {
    return authService.signIn(signInRequestDto, response);
  }

  /**
   * 로그아웃 -> redis 속 리프레시 토큰 삭제
   *
   * @param accountDetails
   */
  @DeleteMapping("/sign-out")
  @ResponseStatus(HttpStatus.OK)
  public void signOut(final @AuthenticationPrincipal AccountDetails accountDetails) {
    authService.signOut(accountDetails.getAccount());
  }

  /**
   * 토큰 재발행
   *
   * @param tokenRequestDto
   * @param response
   * @return
   */
  @PostMapping("/re-issue")
  @ResponseStatus(HttpStatus.OK)
  public SignInResponseDto reissue(
      final @RequestBody TokenRequestDto tokenRequestDto,
      final HttpServletResponse response
  ) {
    return authService.reissue(tokenRequestDto, response);
  }

  /**
   * 비밀번호 재설정 이메일 전송
   *
   * @param email
   */
  @PostMapping("/mail")
  @ResponseStatus(HttpStatus.OK)
  public void verify(final @RequestParam String email) {
    authService.sendVerificationMail(email);
  }

  /**
   * 인증링크 확인
   *
   * @param email
   * @param code
   */
  @PostMapping("/mail/{code}")
  @ResponseStatus(HttpStatus.OK)
  public void getVerify(final @RequestParam String email, final @PathVariable String code) {
    authService.verifyEmail(email, code);
  }

}
