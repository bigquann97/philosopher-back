package gladiator.philosopher.account.controller;

import gladiator.philosopher.account.dto.EditProfileRequestDto;
import gladiator.philosopher.account.service.ProfileService;
import gladiator.philosopher.security.members.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

  private final ProfileService profileService;

  // 내 정보 수정
  @PutMapping
  public void editProfile(
      final EditProfileRequestDto req,
      final @AuthenticationPrincipal MemberDetails memberDetails
  ) {
    profileService.editProfile(req, memberDetails.getMember());
  }

}
