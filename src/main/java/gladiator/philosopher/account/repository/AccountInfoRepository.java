package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {

  AccountInfo getAccountInfoByAccountId(Long id);

}
