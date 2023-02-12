package gladiator.philosopher.account.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;

public class AccountCustomRepositoryImpl implements AccountCustomRepository{

  private final JPAQueryFactory jpaQueryFactory;

  public AccountCustomRepositoryImpl(EntityManager em){
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }



}
