package gladiator.philosopher.thread.repository;

import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.category.entity.QCategory;
import gladiator.philosopher.comment.entity.QComment;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.recommend.entity.QThreadRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.QThread;
import gladiator.philosopher.thread.entity.QThreadImage;
import gladiator.philosopher.thread.entity.QThreadOpinion;
import gladiator.philosopher.thread.entity.Sort;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadLocation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ThreadCustomRepositoryImpl extends QuerydslRepositorySupport implements
    ThreadCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QThread thread;
  private final QThreadRecommend threadRecommend;
  private final QComment comment;
  private final QAccount account;
  private final QCategory category;
  private final QThreadImage threadImage;
  private final QThreadOpinion threadOpinion;

  public ThreadCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.thread = QThread.thread;
    this.threadRecommend = QThreadRecommend.threadRecommend;
    this.comment = QComment.comment;
    this.account = QAccount.account;
    this.category = QCategory.category;
    this.threadImage = QThreadImage.threadImage;
    this.threadOpinion = QThreadOpinion.threadOpinion;
  }

  @Override
  public Optional<ThreadResponseDto> selectThread(Long id) {
    ThreadResponseDto dto = jpaQueryFactory
        .select(
            Projections.constructor(
                ThreadResponseDto.class,
                thread.id,
                thread.title,
                thread.content,
                account.nickname,
                thread.createdDate,
                thread.endDate,
                category.name,
                JPAExpressions
                    .select(Wildcard.count)
                    .from(comment)
                    .where(comment.thread.id.eq(thread.id)),
                JPAExpressions
                    .select(Wildcard.count)
                    .from(threadRecommend)
                    .where(threadRecommend.thread.id.eq(thread.id))
            ))
        .from(thread)
        .leftJoin(account).on(thread.account.id.eq(account.id))
        .leftJoin(category).on(thread.category.id.eq(category.id))
        .where(thread.id.eq(id))
        .fetchFirst();

    List<String> images = jpaQueryFactory
        .select(threadImage.imageUrl)
        .from(threadImage)
        .where(threadImage.thread.id.eq(id))
        .fetch();

    List<String> opinions = jpaQueryFactory
        .select(threadOpinion.opinion)
        .from(threadOpinion)
        .where(threadOpinion.thread.id.eq(id))
        .fetch();

    if (dto != null) {
      dto.addImages(images);
      dto.addOpinions(opinions);
      return Optional.of(dto);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public MyPage<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<ThreadSimpleResponseDto> dtos = jpaQueryFactory
        .select(
            Projections.constructor(
                ThreadSimpleResponseDto.class,
                thread.id,
                thread.title,
                category.name,
                JPAExpressions
                    .select(Wildcard.count)
                    .from(comment)
                    .where(comment.thread.id.eq(thread.id)),
                JPAExpressions
                    .select(Wildcard.count)
                    .from(threadRecommend)
                    .where(threadRecommend.thread.id.eq(thread.id)),
                account.nickname,
                thread.createdDate,
                thread.endDate
            ))
        .from(thread)
        .leftJoin(account).on(thread.account.id.eq(account.id))
        .leftJoin(category).on(thread.category.id.eq(thread.id))
//        .leftJoin(threadRecommend).on(threadRecommend.thread.id.eq(thread.id)).groupBy(thread.id)
        .where(
            threadStatusEq(ThreadLocation.CONTINUE),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .orderBy(
            orderByCondSort(cond.getSort()),
            orderByCondSort(Sort.NEW)
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = jpaQueryFactory
        .select(Wildcard.count)
        .from(thread)
        .where(
            threadStatusEq(ThreadLocation.CONTINUE),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond))
        .fetch().get(0);

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }


  @Override
  public MyPage<ThreadSimpleResponseDto> selectArchivedThreadWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<ThreadSimpleResponseDto> dtos = jpaQueryFactory
        .select(
            Projections.constructor(
                ThreadSimpleResponseDto.class,
                thread.id,
                thread.title,
                thread.category,
                JPAExpressions
                    .select(Wildcard.count)
                    .from(comment)
                    .where(comment.thread.id.eq(thread.id)),
                JPAExpressions
                    .select(Wildcard.count)
                    .from(threadRecommend)
                    .where(threadRecommend.thread.id.eq(thread.id)),
                account.nickname,
                thread.createdDate,
                thread.endDate
            ))
        .from(thread)
        .leftJoin(thread.account, account)
        .leftJoin(threadRecommend).on(threadRecommend.thread.id.eq(thread.id)).groupBy(thread.id)
        .where(
            threadStatusEq(ThreadLocation.ARCHIVED),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .orderBy(
            orderByCondSort(cond.getSort()),
            orderByCondSort(Sort.NEW)
        ).fetch();

    Long total = jpaQueryFactory
        .select(Wildcard.count)
        .from(thread)
        .leftJoin(thread.account, account)
        .leftJoin(threadRecommend).on(threadRecommend.thread.id.eq(thread.id)).groupBy(thread.id)
        .where(
            threadStatusEq(ThreadLocation.ARCHIVED),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .orderBy(
            orderByCondSort(cond.getSort()),
            orderByCondSort(Sort.NEW)
        ).fetch().get(0);

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }

  private OrderSpecifier<Long> orderByCondSort(Sort sort) {
    if (sort.equals(Sort.NEW)) {
      return thread.id.desc();
    } else {
      return threadRecommend.count().desc();
    }
  }

  private BooleanExpression categoryEq(ThreadSearchCond cond) {
    if (cond.getCategoryId() == 0) {
      return null;
    } else {
      return thread.category.id.eq(cond.getCategoryId());
    }
  }

  private BooleanExpression titleOrContentContainsWord(String word) {
    return hasText(word) ? thread.content.containsIgnoreCase(word)
        .or(thread.content.containsIgnoreCase(word)) : null;
  }

  private BooleanExpression threadStatusEq(ThreadLocation location) {
    return thread.location.eq(location);
  }


}

// ===============================================

/*

  @Override
  public Optional<ThreadResponseDto> selectThread(Long id) {
    Thread resultThread = jpaQueryFactory
        .select(thread)
        .from(thread)
        .leftJoin(thread.threadImages).fetchJoin()
        .leftJoin(thread.recommends).fetchJoin()
        .leftJoin(thread.opinions).fetchJoin()
        .where(thread.id.eq(id))
        .fetchOne();
    ThreadResponseDto dto = ThreadResponseDto.of(resultThread);
    return Optional.ofNullable(dto);
  }

  @Override
  public Page<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<Thread> query = query(thread, cond, ThreadLocation.CONTINUE)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Thread> threads = query.fetch();
    Long totalSize = countQuery(cond, ThreadLocation.CONTINUE).fetch().get(0);

    return new PageImpl<>(ThreadSimpleResponseDto.of(threads), pageable, totalSize);
  }

    @Override
  public Page<ThreadSimpleResponseDto> selectArchivedThreadWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<Thread> query = query(thread, cond, ThreadLocation.ARCHIVED)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Thread> threads = query.fetch();
    Long totalSize = countQuery(cond, ThreadLocation.ARCHIVED).fetch().get(0);

    return new PageImpl<>(ThreadSimpleResponseDto.of(threads), pageable, totalSize);
    return null;
  }

    private <T> JPAQuery<T> query(Expression<T> expr, ThreadSearchCond cond,
      ThreadLocation location) {
    return jpaQueryFactory
        .select(expr)
        .from(thread)
        .leftJoin(thread.recommends, threadRecommend).fetchJoin()
        .leftJoin(thread.account).fetchJoin()
        .where(
            threadStatusEq(location),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .orderBy(
            orderByCondSort(cond.getSort()),
            orderByCondSort(Sort.NEW)
        )
        .groupBy(thread.id)
        ;
  }

  private JPAQuery<Long> countQuery(ThreadSearchCond cond, ThreadLocation location) {
    return jpaQueryFactory
        .select(Wildcard.count)
        .from(thread)
        .leftJoin(thread.recommends, threadRecommend)
        .where(
            threadStatusEq(location),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .orderBy(
            orderByCondSort(cond.getSort()),
            orderByCondSort(Sort.NEW)
        )
        .groupBy(thread.id)
        ;
  }




 */