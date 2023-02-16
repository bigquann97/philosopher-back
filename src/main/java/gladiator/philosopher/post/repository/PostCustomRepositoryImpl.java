package gladiator.philosopher.post.repository;

import static gladiator.philosopher.account.entity.QAccount.account;
import static gladiator.philosopher.post.entity.QPost.post;
import static gladiator.philosopher.recommend.entity.QRecommend.recommend;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostStatus;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

@Slf4j
public class PostCustomRepositoryImpl implements PostCustomRepository {

  public final JPAQueryFactory jpaQueryFactory;

  public PostCustomRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<TestPostResponseDto> searchPost(PostSearchCondition condition, Pageable pageable) {

    List<TestPostResponseDto> data = jpaQueryFactory.select
            (Projections.constructor(TestPostResponseDto.class,
                post.id.as("id"),
                post.title.as("title"),
                post.content.as("content"),
                post.category.name.as("category"),
                post.createdDate.as("createDate"),
                post.status.as("status"),
                account.nickname.as("nickname"),
                JPAExpressions
                    .select(Wildcard.count).from(recommend)
                    .where(post.id.eq(recommend.post.id)))
            )
        .from(post)
        .leftJoin(post.account, account)
        .where(
            postContentEqual(condition.getContent()),
            postStatusEqual(condition.getStatus()),
            postCategoryEqual(condition.getCategory()),
            postTitleEqual(condition.getTitle())
        )
        .groupBy(post.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return data;

    // postimage 속에 있는 post id를 가지고

  }

  private BooleanExpression postStatusEqual(String status) {
    return hasText(status) ? post.status.eq(PostStatus.valueOf(status)) : null;
  }

  private BooleanExpression postCategoryEqual(String category) {
    return hasText(category) ? post.category.name.eq(category) : null;
  }

  private BooleanExpression postContentEqual(String content) {
    return hasText(content) ? post.content.contains(content) : null;
  }

  private BooleanExpression postTitleEqual(String title) {
    return hasText(title) ? post.title.contains(title) : null;
  }

}
