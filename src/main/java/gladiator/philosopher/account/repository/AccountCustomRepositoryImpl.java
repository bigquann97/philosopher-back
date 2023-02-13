package gladiator.philosopher.account.repository;

import static gladiator.philosopher.account.entity.QAccount.account;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.account.entity.QAccountImage;
import gladiator.philosopher.admin.dto.QUserInfoResponseDto;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import java.util.List;
import javax.persistence.EntityManager;

public class AccountCustomRepositoryImpl implements AccountCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  public AccountCustomRepositoryImpl(EntityManager em){
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<UserInfoResponseDto> searchAccount(AccountSearchCondition condition) {
    return jpaQueryFactory.select(new QUserInfoResponseDto(
            account.id,
            account.email,
            account.age,
            account.nickname,
            account.gender,
            account.type,
            account.status
        ))
        .from(account)
        .where(
            userTypeEqual(condition.getUserType()),
            userStatsEqual(condition.getUserStatus()),
            userGenderEqual(condition.getUserGender()))
        .fetch();
  }

  private BooleanExpression userGenderEqual(String userGender) {
    return hasText(userGender)?account.gender.eq(Gender.valueOf(userGender)):null;
  }

  private BooleanExpression userStatsEqual(String userStatus) {
    return hasText(userStatus)?account.status.eq(UserStatus.valueOf(userStatus)):null;
  }

  private BooleanExpression userTypeEqual(String userType) {
    return hasText(userType)?account.type.eq(UserRole.valueOf(userType)):null;
  }
}
