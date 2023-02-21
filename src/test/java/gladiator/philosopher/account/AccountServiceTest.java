package gladiator.philosopher.account;

import static org.assertj.core.api.Assertions.assertThat;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.account.service.AccountService;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.factory.AccountFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
class AccountServiceTest {

  @Autowired
  AccountService accountService;

  @Autowired
  AccountRepository accountRepository;

  @Test
  @DisplayName("1. 사용자 정보 가지고 오기 ")
  public void getAccountInfoCheck() {

    //given
    Account account = AccountFactory.createUserAccount1();

//    Account mango = Account.builder()
//        .email("qwer@naver.com")
//        .password("rlawlghks1")
//        .gender(Gender.MALE)
//        .status(UserStatus.ACTIVATED)
//        .role(UserRole.ROLE_USER)
//        .age(15)
//        .nickname("김망고")
//        .build();
//    accountRepository.save(mango);
    accountRepository.save(account);

    //when
//    Account getAccount = accountService.getAccount(mango.getId());

    //then
//    log.info(mango.getEmail());
//    assertThat(getAccount.getEmail().equals(mango.getEmail()));
    assertThat(account.getEmail().equals("kiang18@naver.com"));

  }


}
