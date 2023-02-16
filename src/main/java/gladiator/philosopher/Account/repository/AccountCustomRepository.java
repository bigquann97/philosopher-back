package gladiator.philosopher.Account.repository;

import gladiator.philosopher.Account.dto.AccountSearchCondition;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import java.util.List;

public interface AccountCustomRepository {

  List<UserInfoByAdminResponseDto> searchAccount(AccountSearchCondition condition);

}
