package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import java.util.List;

public interface AccountCustomRepository {

  List<UserInfoByAdminResponseDto> searchAccount(AccountSearchCondition condition);

}
