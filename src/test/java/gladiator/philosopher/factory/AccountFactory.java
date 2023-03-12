package gladiator.philosopher.factory;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.enums.Gender;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.account.enums.UserStatus;


public class AccountFactory {

  public static Account createUserAccount1() {
    return Account.builder()
        .age(20)
        .email("temp@naver.com")
        .gender(Gender.FEMALE)
        .status(UserStatus.ACTIVATED)
        .password("temp")
        .nickname("nick")
        .role(UserRole.ROLE_USER)
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
        .role(UserRole.ROLE_USER)
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
        .role(UserRole.ROLE_MANAGER)
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
        .role(UserRole.ROLE_ADMIN)
        .build();
  }

}
