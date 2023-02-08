package gladiator.philosopher.account.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.enumtype.GenderType;
import gladiator.philosopher.common.enumtype.UserStatus;
import gladiator.philosopher.common.enumtype.UserType;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class accountRequestDto {

  @Size(min = 4, max = 12, message = "아이디는 4에서 12자 사이 입니다.")
  @Pattern(regexp = "[a-z0-9]*$", message = "아이디 형식이 일치하지 않습니다.")
  private final String email;

  @Size(min = 8, max = 15, message = "비밀번호는 8에서 15자 사이 입니다.")
  @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$", message = "비밀번호 형식이 일치하지 않습니다.")
  private final String password;

  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하 입니다.")
  private final String nickName;

  private final Long age;

  private final String gender;

  public Account toEntity(String password, GenderType genderType) {
    return Account.builder()
        .email(this.getEmail())
        .password(password)
        .age(this.getAge())
        .nickName(this.getNickName())
        .gender(genderType)
        .type(UserType.ROLE_USER)
        .status(UserStatus.ACTIVATED).build();
  }

  public GenderType checkGender(String gender) {
    if (gender.equals("MALE")) {
      return GenderType.MALE;
    } else {
      return GenderType.FEMALE;
    }
  }

}
