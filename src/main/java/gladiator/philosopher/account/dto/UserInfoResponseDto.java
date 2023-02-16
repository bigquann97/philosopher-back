package gladiator.philosopher.account.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserStatus;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private final String email;
  private final Integer age;
  private final String nickname;
  private final Gender gender;
  private final UserStatus userStatus;

  public UserInfoResponseDto(final Account account) {
    this.email = account.getEmail();
    this.age = account.getAge();
    this.nickname = account.getNickname();
    this.gender = account.getGender();
    this.userStatus = account.getStatus();
  }

}
