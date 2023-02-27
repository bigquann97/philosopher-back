package gladiator.philosopher.account.repository;

import static gladiator.philosopher.account.entity.QAccount.account;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.dto.AccountSearchCondition;
import gladiator.philosopher.admin.dto.QUserInfoByAdminResponseDto;
import gladiator.philosopher.admin.dto.UserInfoByAdminResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.account.enums.Gender;
import gladiator.philosopher.account.enums.UserRole;
import gladiator.philosopher.account.enums.UserStatus;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

public class AccountCustomRepositoryImpl implements AccountCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public AccountCustomRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }


  @Override
  public MyPage<UserInfoByAdminResponseDto> searchAccount(AccountSearchCondition condition,
      Pageable pageable) {
    final List<UserInfoByAdminResponseDto> fetch = jpaQueryFactory.select(
            new QUserInfoByAdminResponseDto(
                account.id,
                account.email,
                account.age,
                account.nickname,
                account.gender,
                account.role,
                account.status
            ))
        .from(account)
        .where(
            userTypeEqual(condition.getRole()),
            userStatsEqual(condition.getStatus()),
            userGenderEqual(condition.getGender()))
        .orderBy(account.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    final JPAQuery<Long> count = jpaQueryFactory.select(account.count()).from(account).where(
        userTypeEqual(condition.getRole()),
        userStatsEqual(condition.getStatus()),
        userGenderEqual(condition.getGender()));

    return new MyPage<>(new PageImpl<>(fetch, pageable, count.fetchOne()));
  }

  private BooleanExpression userGenderEqual(Gender gender) {
    if(ObjectUtils.isEmpty(gender)){
      return null;
    }return account.gender.eq(gender);
  }

  private BooleanExpression userStatsEqual(UserStatus status) {
    if(ObjectUtils.isEmpty(status)){
      return null;
    }return account.status.eq(status);
  }

  private BooleanExpression userTypeEqual(UserRole role) {
    if(ObjectUtils.isEmpty(role)){
      return null;
    }return account.role.eq(role);
  }


}
