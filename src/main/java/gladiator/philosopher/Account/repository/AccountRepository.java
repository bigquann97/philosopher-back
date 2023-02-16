package gladiator.philosopher.Account.repository;

import gladiator.philosopher.Account.entity.Account;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountCustomRepository{

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickName);

  Optional<Account> findByEmail(String email);

  @Query("select new gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto(a.id, a.email, a.age, a.nickname, a.gender, a.type, a.status) from Account a")
  List<UserInfoByAdminResponseDto> getInfoByAccount();



  

}
