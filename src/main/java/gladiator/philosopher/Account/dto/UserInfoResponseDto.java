package gladiator.philosopher.Account.dto;

import gladiator.philosopher.Account.entity.Account;
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


  public UserInfoResponseDto(Account account){
    this.email = account.getEmail();
    this.age = account.getAge();
    this.nickname = account.getNickname();
    this.gender = account.getGender();
    this.userStatus = account.getStatus();
  }

}
