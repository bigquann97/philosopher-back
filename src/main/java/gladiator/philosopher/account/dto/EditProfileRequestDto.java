package gladiator.philosopher.account.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class EditProfileRequestDto {

  private final String nickname;

}
