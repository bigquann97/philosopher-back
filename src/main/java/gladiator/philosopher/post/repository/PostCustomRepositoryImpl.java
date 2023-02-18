package gladiator.philosopher.post.repository;

import static gladiator.philosopher.account.entity.QAccount.account;
import static gladiator.philosopher.post.entity.QPost.post;
import static gladiator.philosopher.recommend.entity.QPostRecommend.postRecommend;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@Slf4j
public class PostCustomRepositoryImpl implements PostCustomRepository {

  public final JPAQueryFactory jpaQueryFactory;

  public PostCustomRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Page<PostResponseDtoByQueryDsl> searchPost(PostSearchCondition condition, Pageable pageable) {

    final List<PostResponseDtoByQueryDsl> fetch = jpaQueryFactory.select
            (Projections.constructor(PostResponseDtoByQueryDsl.class,
                post.id,
                post.title,
                post.category.name,
                post.createdDate,
                post.status,
                account.nickname,
                JPAExpressions
                    .select(Wildcard.count).from(postRecommend)
                    .where(post.id.eq(postRecommend.post.id))
            ))
        .from(post)
        .leftJoin(post.account, account)
        .where(
            postContentAndTitleEqual(condition.getWord()),
            postCategoryEqual(condition.getCategoryId())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(post.id.desc())
        .fetch();

    final JPAQuery<Long> count = jpaQueryFactory.select(post.count())
        .from(post)
        .where(
            postContentAndTitleEqual(condition.getWord()),
            postCategoryEqual(condition.getCategoryId())
        );

    return new PageImpl<>(fetch, pageable, count.fetchOne());

  }

  private BooleanExpression postCategoryEqual(Long categoryId) {
    return categoryId != null ? post.category.id.eq(categoryId) : null;
  }

  private BooleanExpression postContentAndTitleEqual(String word) {
    return hasText(word) ? post.content.containsIgnoreCase(word).or(post.title.containsIgnoreCase(word)) : null;
  }



}
