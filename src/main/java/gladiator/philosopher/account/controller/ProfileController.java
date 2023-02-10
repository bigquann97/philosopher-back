package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.EditProfileRequestDto;
import gladiator.philosopher.account.service.ProfileService;
import gladiator.philosopher.common.security.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

  private final ProfileService profileService;

  // 내 정보 수정
  @PutMapping
  public void editProfile(
      @RequestPart("image") MultipartFile multipartFile,
      @RequestPart("req") final EditProfileRequestDto req,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    System.out.println(req.getNickname() + "AAA");
    profileService.editProfile(multipartFile, req, accountDetails.getAccount());
  }

}
