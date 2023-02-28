package gladiator.philosopher.account.dto.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force=true, access = AccessLevel.PRIVATE)
public class ModifyAccountNicknameRequestDto {

  private final String nickname;

  public ModifyAccountNicknameRequestDto(String nickname) {
    this.nickname = nickname;
  }
}
