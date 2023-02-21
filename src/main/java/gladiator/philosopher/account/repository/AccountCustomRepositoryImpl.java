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
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
            userTypeEqual(condition.getUserType()),
            userStatsEqual(condition.getUserStatus()),
            userGenderEqual(condition.getUserGender()))
        .orderBy(account.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    final JPAQuery<Long> count = jpaQueryFactory.select(account.count()).from(account).where(
        userTypeEqual(condition.getUserType()),
        userStatsEqual(condition.getUserStatus()),
        userGenderEqual(condition.getUserGender()));

    return new MyPage<>(new PageImpl<>(fetch, pageable, count.fetchOne()));
  }

  private BooleanExpression userGenderEqual(String userGender) {
    return hasText(userGender) ? account.gender.eq(Gender.valueOf(userGender)) : null;
  }

  private BooleanExpression userStatsEqual(String userStatus) {
    return hasText(userStatus) ? account.status.eq(UserStatus.valueOf(userStatus)) : null;
  }

  private BooleanExpression userTypeEqual(String userType) {
    return hasText(userType) ? account.role.eq(UserRole.valueOf(userType)) : null;
  }


}
