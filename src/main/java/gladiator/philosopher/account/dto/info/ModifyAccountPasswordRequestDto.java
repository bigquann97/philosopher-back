package gladiator.philosopher.account.dto.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyAccountPasswordRequestDto {

  private final String password;

  public ModifyAccountPasswordRequestDto(String password) {
    this.password = password;
  }
}
