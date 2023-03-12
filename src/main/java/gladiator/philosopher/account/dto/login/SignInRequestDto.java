package gladiator.philosopher.account.dto.login;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class SignInRequestDto {

  @Size(min = 4, max = 10)
  @Pattern(regexp = "^[a-z0-9]*$")
  private final String email;

  @Size(min = 4, max = 10)
  @Pattern(regexp = "^[a-zA-Z0-9]*$")
  private final String password;

}
