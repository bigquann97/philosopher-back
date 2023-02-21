package gladiator.philosopher.account.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class AccountSearchCondition {

  private final String userType;
  private final String userGender;
  private final String userStatus;

}
