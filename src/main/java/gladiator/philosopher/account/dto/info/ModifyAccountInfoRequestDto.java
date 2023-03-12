package gladiator.philosopher.account.dto.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyAccountInfoRequestDto {

  private final String nickname;
  private final String password;

  public ModifyAccountInfoRequestDto(String nickname, String password) {
    this.nickname = nickname;
    this.password = password;
  }
}
