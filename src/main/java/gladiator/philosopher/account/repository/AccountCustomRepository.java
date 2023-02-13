package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import java.util.List;

public interface AccountCustomRepository {

  List<UserInfoResponseDto> searchAccount(AccountSearchCondition condition);

}
