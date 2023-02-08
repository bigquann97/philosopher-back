package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.entity.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickName);

  Optional<Account> findByEmail(String email);

}
