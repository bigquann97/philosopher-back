package gladiator.philosopher.temp.account.repository;

import gladiator.philosopher.temp.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsByEmail(String email);

  boolean existsByNickName(String nickName);

  Optional<Account> findByEmail(String email);

}
