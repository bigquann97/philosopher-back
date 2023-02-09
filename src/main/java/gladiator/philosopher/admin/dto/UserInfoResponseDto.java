package gladiator.philosopher.admin.dto;

import gladiator.philosopher.common.enumtype.GenderType;
import gladiator.philosopher.common.enumtype.UserStatus;
import gladiator.philosopher.common.enumtype.UserType;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private final Long id;
  private final String email;
  private final int age;
  private final String nickname;
  private final GenderType genderType;
  private final UserType userType;
  private final UserStatus userStatus;

  public UserInfoResponseDto(Long id, String email, int age, String nickname,
      GenderType genderType, UserType userType,
      UserStatus userStatus) {
    this.id = id;
    this.email = email;
    this.age = age;
    this.nickname = nickname;
    this.genderType = genderType;
    this.userType = userType;
    this.userStatus = userStatus;
  }
}
