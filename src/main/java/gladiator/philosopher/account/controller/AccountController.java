package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.dto.RecommendCommentResponseDto;
import gladiator.philosopher.account.dto.SimpleResponseDtoByThread;
import gladiator.philosopher.account.dto.info.ModifyAccountNicknameRequestDto;
import gladiator.philosopher.account.dto.info.ModifyAccountPasswordRequestDto;
import gladiator.philosopher.account.dto.info.UserInfoResponseDto;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.entity.PageRequest;
import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
   * 내 정보 가지고 오기
   *
   * @param accountDetails
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public UserInfoResponseDto getMyInfo(
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    return accountService.getMyInfo(accountDetails.getAccount());
  }

  /**
   * 내가 쓴 댓글 가지고 오기
   *
   * @param accountDetails
   * @param pageRequest
   * @return
   */
  @GetMapping("/comments")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<CommentSimpleResponseDto> getMyComments(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return accountService.getMyComments(accountDetails.getAccount().getId(), pageable);
  }

  /**
   * 내가 쓴 게시글 가지고 오기
   *
   * @param accountDetails
   * @param pageRequest
   * @return
   */
  @GetMapping("/posts")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<PostSimpleResponseDto> getMyPosts(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return accountService.getMyPosts(accountDetails.getAccount().getId(), pageable);
  }

  /**
   * 내가 좋아요 누른 게시글 가지고 오기
   *
   * @param accountDetails
   * @param pageRequest
   * @return
   */
  @GetMapping("/recommend/post")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<PostSimpleResponseDto> getRecommendPostsByAccount
  (
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return accountService.getRecommendPostsByAccount(accountDetails.getAccount().getId(), pageable);
  }

  /**
   * 내가 좋아요 누른 쓰레드 가지고 오기
   * @param accountDetails
   * @param pageRequest
   * @return
   */
  @GetMapping("/recommend/thread")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<SimpleResponseDtoByThread> getRecommendThreadsByAccount(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return accountService.getRecommendThreadsByAccount(accountDetails.getAccount().getId(), pageable);
  }

  /**
   * 내가 좋아요 누른 댓글 가지고 오기
   * @param accountDetails
   * @param pageRequest
   * @return
   */
  @GetMapping("/recommend/comment")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<RecommendCommentResponseDto> getRecommendCommentsByAccount(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final PageRequest pageRequest
  ) {
    Pageable pageable = pageRequest.of();
    return accountService.getRecommendCommentsByAccount(accountDetails.getAccount().getId(), pageable);
  }

  /**
   * 비밀번호 변경
   *
   * @param accountDetails
   * @param infoRequestDto
   * @return
   */
  @PatchMapping("/password/modify")
  @ResponseStatus(HttpStatus.CREATED)
  public Long modifyAccountPassword(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final @RequestBody ModifyAccountPasswordRequestDto infoRequestDto
  ) {
    return accountService.modifyAccountPassword(accountDetails.getAccount(), infoRequestDto.getPassword());
  }

  /**
   * 닉네임 변경
   *
   * @param accountDetails
   * @param infoRequestDto
   * @return
   */
  @PatchMapping("/nickname/modify")
  @ResponseStatus(HttpStatus.CREATED)
  public Long modifyAccountNickname(
      final @AuthenticationPrincipal AccountDetails accountDetails,
      final @RequestBody ModifyAccountNicknameRequestDto infoRequestDto
  ) {
    return accountService.modifyAccountNickname(accountDetails.getAccount(),
        infoRequestDto.getNickname());
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

