package gladiator.philosopher.Account.repository;

import gladiator.philosopher.Account.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountInfoRepository extends JpaRepository<AccountInfo, Long> {

}
