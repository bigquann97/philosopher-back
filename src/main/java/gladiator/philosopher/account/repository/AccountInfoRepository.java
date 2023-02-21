package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.entity.AccountImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountImage, Long> {

  AccountImage getAccountInfoByAccountId(Long id);

}
