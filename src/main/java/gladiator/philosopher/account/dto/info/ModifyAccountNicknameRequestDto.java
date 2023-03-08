package gladiator.philosopher.account.dto.info;

import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyAccountNicknameRequestDto {

  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하 입니다.")
  private final String nickname;

  public ModifyAccountNicknameRequestDto(String nickname) {
    this.nickname = nickname;
  }
  
}
