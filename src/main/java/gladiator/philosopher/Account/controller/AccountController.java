package gladiator.philosopher.Account.controller;

import gladiator.philosopher.Account.service.AccountService;
import gladiator.philosopher.Account.dto.ModifyProfileRequestDto;
import gladiator.philosopher.common.security.AccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/accounts")
public class AccountController {

  private final AccountService accountService;

  /**
   * 내 정보 가지고 오기 -> 내용 더 추가할 것.
   * @param accountDetails
   */
  @GetMapping("")
  @ResponseStatus(HttpStatus.OK)
  public void getMyInfo(@AuthenticationPrincipal AccountDetails accountDetails){
    accountService.getMyInfo(accountDetails.getAccount());
  }

  /**
   * 내 정보 수정
   * @param accountDetails
   * @param modifyProfileRequestDto
   */
  @PatchMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public void modifyMyInfo(@AuthenticationPrincipal AccountDetails accountDetails, ModifyProfileRequestDto modifyProfileRequestDto){
    accountService.modifyInfo(accountDetails.getAccount(), modifyProfileRequestDto);
  }

}