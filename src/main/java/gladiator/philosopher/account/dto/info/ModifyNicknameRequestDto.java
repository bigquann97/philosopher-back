package gladiator.philosopher.account.dto.info;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyNicknameRequestDto {

  private final String nickname;

}
