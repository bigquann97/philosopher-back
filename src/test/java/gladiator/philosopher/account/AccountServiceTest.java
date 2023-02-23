package gladiator.philosopher.account;


import gladiator.philosopher.account.dto.info.ModifyAccountInfoRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.enums.Gender;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.account.enums.UserStatus;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class AccountServiceTest {

  @Autowired
  AccountServiceImpl accountService;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  AccountInfoRepository accountInfoRepository;

  @DisplayName("1. 사용자 정보 가지고 오기 ( 완 )")
  @Test
  void getAccountInfoTest_성공() {
    //when
    Account account = new Account(1L, "qwer@naver.com", "rlawlghks1", 10, "지팡구", Gender.FEMALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    //then
    Account getAccount = accountService.getAccount(account.getId());

    //given
    Assertions.assertThat(getAccount.getAge()).isEqualTo(10);
    Assertions.assertThat(getAccount.getAge()).isEqualTo(15); // 실패 케이스
  }

  @DisplayName("2. 권한 수정 - 어드민 (일반 유저 -> 매니저, 매니저 -> 유저) ( 완 )")
  @Test
  void updateAccountRoleTest_성공() {
    //when
    Account account = new Account(1L, "qwer@naver.com", "rlawlghks1", 10, "지팡구", Gender.FEMALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    //then
    accountService.updateAccountRole(account);

    //given
    Assertions.assertThat(account.getRole()).isEqualTo(UserRole.ROLE_USER); // 실패 케이스
    Assertions.assertThat(account.getRole()).isEqualTo(UserRole.ROLE_MANAGER);
  }

  @DisplayName("3. 내 정보 가지고 오기 ")
  @Test
  void geyMyInfoTest_성공() {
    //when
    Account account = new Account(1L, "qwer@naver.com", "rlawlghks1", 10, "지팡구", Gender.FEMALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    //then
    accountService.getMyInfo(account);

    //given
    Assertions.assertThat(account.getNickname()).isEqualTo("지팡구");
    Assertions.assertThat(account.getNickname()).isEqualTo("김지환");// 실패 케이스
  }

  @DisplayName("4. 내 정보(비밀번호, 닉네임) 수정 ")
  @Test
  @Rollback
  void modifyAccountInfoTest_완료() {
    //when
    Account account = new Account(1L, "qwer@naver.com", "rlawlghks1", 10, "지팡구", Gender.FEMALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    ModifyAccountInfoRequestDto modifyAccountInfoRequestDto = new ModifyAccountInfoRequestDto(
        "변경된 닉네임 입니디.", "rlawlghks2");
    //then
    accountService.modifyAccountInfo(account, modifyAccountInfoRequestDto);

    //given
    Assertions.assertThat(account.getNickname()).isEqualTo("지팡구");  // 테스트 성공
  }

  @DisplayName("5. 내 정보(프로필 이미지) 수정 ")
  @Test
  void modifyAccountImage_완료() {
    //when
    Account account = new Account(1L, "qwer@naver.com", "rlawlghks1", 10, "지팡구", Gender.FEMALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    AccountImage image = new AccountImage(account, "default_image");
    System.out.println("변경 전 이미지 데이터 : "+image.getImageUrl());
    accountRepository.save(account);
    accountInfoRepository.save(image);
    //then
    accountService.modifyAccountImage(account, "qwer1234");
    System.out.println("변경 후 이미지 데이터 : "+image.getImageUrl());

    //given
    Assertions.assertThat(image.getImageUrl()).isEqualTo("qwer1234"); // 성공 케이스

  }


}
