package gladiator.philosopher.account.dto.login;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.common.enums.UserRole;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class SignUpRequestDto {

  @Size(min = 4, max = 30, message = "이메일 형식으로 입력해야합니다.")
  @Email
  private final String email;

  @Size(min = 8, max = 15, message = "비밀번호는 8에서 15자 사이 입니다.")
  @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$", message = "비밀번호 형식이 일치하지 않습니다.")
  private final String password;

  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하 입니다.")
  private final String nickname;

  @Min(0)
  @Max(130)
  private final int age;

  private final String gender;

  public Account toEntity(String password) {
    return Account.builder()
        .email(this.getEmail())
        .password(password)
        .age(this.getAge())
        .nickname(this.getNickname())
        .gender(checkGender(this.gender))
        .role(UserRole.ROLE_USER)
        .status(UserStatus.ACTIVATED).build();
  }

  public Gender checkGender(String gender) {
    if (gender.equals("MALE")) {
      return Gender.MALE;
    } else {
      return Gender.FEMALE;
    }
  }

}
