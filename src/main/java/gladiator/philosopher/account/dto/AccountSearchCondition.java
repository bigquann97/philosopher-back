package gladiator.philosopher.account.dto;

import gladiator.philosopher.account.enums.Gender;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.account.enums.UserStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AccountSearchCondition {

  private UserRole role;
  private Gender gender;
  private UserStatus status;

  public AccountSearchCondition(UserRole role, Gender gender, UserStatus status) {
    this.role = role;
    this.gender = gender;
    this.status = status;
  }
}
