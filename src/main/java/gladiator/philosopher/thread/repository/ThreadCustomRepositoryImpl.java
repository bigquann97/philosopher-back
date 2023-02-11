package gladiator.philosopher.thread.repository;

import static com.querydsl.core.types.dsl.Expressions.list;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.post.entity.QPostImage;
import gladiator.philosopher.recommend.entity.QRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.QThread;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadCustomRepositoryImpl extends QuerydslRepositorySupport implements
    ThreadCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QThread thread;
  private final QRecommend recommend;
  private final QAccount account;
  private final QPostImage postImage;

  public ThreadCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.thread = QThread.thread;
    this.recommend = QRecommend.recommend;
    this.account = QAccount.account;
    this.postImage = QPostImage.postImage;
  }

  public Page<ThreadResponseDto> searchList(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<ThreadSimpleResponseDto> k = jpaQueryFactory
        .select(Projections.constructor(ThreadSimpleResponseDto.class,
            thread.id,
            thread.title,
            recommend.count(),
            account.nickname,
            account.createdDate
        ))
        .from(thread)
        .leftJoin(thread.postImages, postImage)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(thread.id.desc());

    return null;
  }

  public Page<ThreadResponseDto> searchOne(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<ThreadResponseDto> k = jpaQueryFactory
        .select(Projections.constructor(ThreadResponseDto.class,
            thread.id,
            thread.title,
            thread.content,
            thread.createdDate,
            list(
                Projections.fields(
                    String.class,
                    postImage.uniqueName
                )
            ),
            recommend.count(),
            thread.endTime,
            account.nickname
        ))
        .from(thread)
        .leftJoin(thread.postImages, postImage)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(thread.id.desc());

    return null;
  }

  @Override
  public Page<ThreadResponseDto> getThreadsByCondition(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<Thread> threads = jpaQueryFactory.selectFrom(thread)
        .where(
            searchByTitle(cond.getWord())
                .or(searchByContent(cond.getWord()))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long threadCount = jpaQueryFactory.select(Wildcard.count)
        .from(thread)
        .where(
            searchByTitle(cond.getWord()),
            searchByContent(cond.getWord())
        ).fetch().get(0);

    return new PageImpl<>(ThreadResponseDto.of(threads), pageable, threadCount);
  }

  @Override
  public Page<ThreadResponseDto> getThreadsByRecommendation(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<Thread> threads = jpaQueryFactory.selectFrom(thread)
        .where(
            searchByTitle(cond.getWord())
                .or(searchByContent(cond.getWord()))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(thread.createdDate.asc())
        .fetch();

    Long threadCount = jpaQueryFactory.select(Wildcard.count)
        .from(thread)
        .where(
            searchByTitle(cond.getWord()),
            searchByContent(cond.getWord())
        ).fetch().get(0);

    return new PageImpl<>(ThreadResponseDto.of(threads), pageable, threadCount);
  }

  @Override
  public Page<ThreadResponseDto> getThreadsByViewCount(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<Thread> threads = jpaQueryFactory.selectFrom(thread)
        .where(
            searchByTitle(cond.getWord())
                .or(searchByContent(cond.getWord()))
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy()
        .fetch();

    Long threadCount = jpaQueryFactory.select(Wildcard.count)
        .from(thread)
        .where(
            searchByTitle(cond.getWord()),
            searchByContent(cond.getWord())
        ).fetch().get(0);

    return new PageImpl<>(ThreadResponseDto.of(threads), pageable, threadCount);
  }

  private Predicate searchByContent(String word) {
    return Objects.nonNull(word) ? thread.content.containsIgnoreCase(word) : null;
  }

  private BooleanExpression searchByTitle(String word) {
    return Objects.nonNull(word) ? thread.title.containsIgnoreCase(word) : null;
  }


}
