package gladiator.philosopher.admin.dto;

import com.querydsl.core.annotations.QueryProjection;
import gladiator.philosopher.account.enums.Gender;
import gladiator.philosopher.account.enums.UserStatus;
import gladiator.philosopher.account.enums.UserRole;
import lombok.Getter;

@Getter
public class UserInfoByAdminResponseDto {

  private final Long id;
  private final String email;
  private final int age;
  private final String nickname;
  private final Gender gender;
  private final UserRole userRole;
  private final UserStatus userStatus;

  @QueryProjection
  public UserInfoByAdminResponseDto(Long id, String email, int age, String nickname,
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
