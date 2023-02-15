package gladiator.philosopher.post.repository;

import static gladiator.philosopher.account.entity.QAccount.account;
import static gladiator.philosopher.post.entity.QPost.post;
import static gladiator.philosopher.recommend.entity.QRecommend.recommend;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostStatus;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.recommend.entity.Recommend;
import java.util.List;
import javax.persistence.EntityManager;

public class PostCustomRepositoryImpl implements PostCustomRepository {

  public final JPAQueryFactory jpaQueryFactory;

  public PostCustomRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<TestPostResponseDto> searchPost(PostSearchCondition condition) {


    return jpaQueryFactory.select(Projections.constructor(TestPostResponseDto.class,
            post.id.as("id"),
            post.title.as("title"),
            post.content.as("content"),
            account.nickname.as("nickname")
//        recommend.count().as("recommend")

        )).from(post)
        .leftJoin(post.account, account)
//        .leftJoin(post.recommends, recommend)
        .where(
            post.account.id.eq(account.id))
        .fetch();
  }

  private BooleanExpression postStatus(String status) {
    return hasText(status) ? post.status.eq(PostStatus.valueOf(status)) : null;
  }

  private BooleanExpression postCategory(String category) {
    return hasText(category) ? post.category.name.eq(category) : null;
  }

  private BooleanExpression postContentEqual(String content) {
    return hasText(content) ? post.content.contains(content) : null;
  }

  private BooleanExpression postTitleEqual(String title) {
    return hasText(title) ? post.title.eq(title) : null;
  }
}
