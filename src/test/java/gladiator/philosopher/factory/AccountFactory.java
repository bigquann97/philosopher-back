package gladiator.philosopher.factory;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;



public class AccountFactory {

  public static Account createUserAccount1() {
    return Account.builder()
        .age(20)
        .email("temp@naver.com")
        .gender(Gender.FEMALE)
        .status(UserStatus.ACTIVATED)
        .password("temp")
        .nickname("nick")
        .type(UserRole.ROLE_USER)
        .build();
  }

  public static Account createUserAccount2() {
    return Account.builder()
        .age(20)
        .email("temp2@naver.com")
        .gender(Gender.MALE)
        .status(UserStatus.ACTIVATED)
        .password("temp2")
        .nickname("nick2")
        .type(UserRole.ROLE_USER)
        .build();
  }

  public static Account createManagerAccount() {
    return Account.builder()
        .age(20)
        .email("manager@naver.com")
        .gender(Gender.FEMALE)
        .status(UserStatus.ACTIVATED)
        .password("manager")
        .nickname("manager")
        .type(UserRole.ROLE_MANAGER)
        .build();
  }

  public static Account createAdminAccount() {
    return Account.builder()
        .age(20)
        .email("admin@naver.com")
        .gender(Gender.FEMALE)
        .status(UserStatus.ACTIVATED)
        .password("admin")
        .nickname("admin")
        .type(UserRole.ROLE_ADMIN)
        .build();
  }

}
