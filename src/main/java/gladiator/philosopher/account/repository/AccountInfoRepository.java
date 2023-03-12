package gladiator.philosopher.account.repository;

import gladiator.philosopher.account.entity.AccountImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountInfoRepository extends JpaRepository<AccountImage, Long> {

  AccountImage getAccountInfoByAccountId(Long id);

  @Query("select ai.imageUrl from AccountImage ai where ai.account.id =:id")
  String getAccountImageById(@Param("id")Long id);

}
