package gladiator.philosopher.account.dto.info;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.enums.Gender;
import gladiator.philosopher.account.enums.UserStatus;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private final String email;
  private final Integer age;
  private final String nickname;
  private final Gender gender;
  private final UserStatus userStatus;
  private final String imageUrl;

  public UserInfoResponseDto(final Account account, String imageUrl) {
    this.email = account.getEmail();
    this.age = account.getAge();
    this.nickname = account.getNickname();
    this.gender = account.getGender();
    this.userStatus = account.getStatus();
    this.imageUrl = imageUrl;
  }

}
