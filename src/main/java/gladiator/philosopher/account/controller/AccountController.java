package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.AccountCommentResponseDto;
import gladiator.philosopher.account.dto.ModifyNicknameRequestDto;
import gladiator.philosopher.account.dto.ModifyPasswordRequestDto;
import gladiator.philosopher.account.dto.UserInfoResponseDto;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/accounts")
public class AccountController {

  private final AccountService accountService;
  private final S3Uploader s3Uploader;
  private final String dirName = "AccountImg";
  private final CommentService commentService;

  /**
   * 내 정보 가지고 오기 -> 내용 더 추가할 것.
   *
   * @param accountDetails
   */ // 기능 수정 할 것
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public UserInfoResponseDto getMyInfo(final @AuthenticationPrincipal AccountDetails accountDetails) {
    return accountService.getMyInfo(accountDetails.getAccount());
  }

  /**
   * 내 댓글 가지고 오기
   *
   * @param accountDetails
   */
  @GetMapping("/comments")
  @ResponseStatus(HttpStatus.OK)
  public List<AccountCommentResponseDto> getMyComments(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      @RequestParam(name = "page") int pageNum) {
    return commentService.findMyComments(accountDetails.getAccount(), pageNum);
  }

  /**
   * 내 정보 수정(닉네임)
   *
   * @param accountDetails
   * @param modifynicknameRequestDto
   */
  @PatchMapping("/nickname")
  @ResponseStatus(HttpStatus.CREATED)
  public void modifyMyNickname(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final @RequestBody ModifyNicknameRequestDto modifynicknameRequestDto
  ) {
    accountService.modifyMyNickname(accountDetails.getAccount(), modifynicknameRequestDto);
  }

  /**
   * 내 정보 수정(비밀번호)
   *
   * @param accountDetails
   * @param modifyPasswordRequestDto
   */
  @PatchMapping("/password")
  @ResponseStatus(HttpStatus.CREATED)
  public void modifyMyPassword(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final @RequestBody ModifyPasswordRequestDto modifyPasswordRequestDto
  ) {
    accountService.modifyMyPassword(accountDetails.getAccount(), modifyPasswordRequestDto);
  }

  /**
   * 내 정보 수정(프로필 이미지)
   *
   * @param accountDetails
   * @param multipartFile
   */
  @PatchMapping("/image")
  @ResponseStatus(HttpStatus.CREATED)
  public void modifyMyImage(
      final @RequestPart("image") MultipartFile multipartFile,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    final String oldUrl = accountService.getOldUrl(accountDetails.getAccount());
    s3Uploader.checkFileExtension(multipartFile);
    final String newUrl = s3Uploader.upLoadFileToSingle(multipartFile, dirName);
    accountService.modifyAccountImage(accountDetails.getAccount(), newUrl);
    s3Uploader.newDeleteS3(oldUrl, dirName);
  }

}

