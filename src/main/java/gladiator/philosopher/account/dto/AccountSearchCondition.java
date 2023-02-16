package gladiator.philosopher.account.dto;

import lombok.Data;

@Data
public class AccountSearchCondition {

  private String userType;
  private String userGender;
  private String userStatus;

}
