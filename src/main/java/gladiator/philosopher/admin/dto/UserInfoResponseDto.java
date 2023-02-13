package gladiator.philosopher.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.common.enums.UserRole;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {

  private final Long id;
  private final String email;
  private final int age;
  private final String nickname;
  private final Gender gender;
  private final UserRole userRole;
  private final UserStatus userStatus;

  @QueryProjection
  public UserInfoResponseDto(Long id, String email, int age, String nickname,
      Gender gender, UserRole userRole,
      UserStatus userStatus) {
    this.id = id;
    this.email = email;
    this.age = age;
    this.nickname = nickname;
    this.gender = gender;
    this.userRole = userRole;
    this.userStatus = userStatus;
  }
}
