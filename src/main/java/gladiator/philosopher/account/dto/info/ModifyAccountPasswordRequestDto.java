package gladiator.philosopher.account.dto.info;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyAccountPasswordRequestDto {

  @Size(min = 8, max = 15, message = "비밀번호는 8에서 15자 사이여야하며, 특수문자가 포함되어야합니다.")
  @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()_=+|{};:,.<>/?]*$", message = "비밀번호 형식이 일치하지 않습니다.")
  private final String password;

  public ModifyAccountPasswordRequestDto(String password) {
    this.password = password;
  }
}
