package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccountCustomRepository {

  MyPage<UserInfoByAdminResponseDto> searchAccount(AccountSearchCondition condition, Pageable pageable);

}
