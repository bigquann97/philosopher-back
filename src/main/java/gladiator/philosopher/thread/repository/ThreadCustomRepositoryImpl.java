package gladiator.philosopher.thread.repository;

import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.account.entity.QAccountImage;
import gladiator.philosopher.admin.dto.QThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.admin.dto.ThreadsSimpleResponseDtoByAdmin;
import gladiator.philosopher.category.entity.QCategory;
import gladiator.philosopher.comment.entity.QComment;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.recommend.entity.QCommentRecommend;
import gladiator.philosopher.recommend.entity.QThreadRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSearchCondByAdmin;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.QThread;
import gladiator.philosopher.thread.entity.QThreadImage;
import gladiator.philosopher.thread.entity.QThreadOpinion;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.enums.Sort;
import gladiator.philosopher.thread.enums.ThreadLocation;
import gladiator.philosopher.thread.enums.ThreadStatus;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
  private final QAccountImage accountImage;
  private final QCommentRecommend commentRecommend;

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
    this.accountImage = QAccountImage.accountImage;
    this.commentRecommend = QCommentRecommend.commentRecommend;
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
                    .where(threadRecommend.thread.id.eq(thread.id)),
                accountImage.imageUrl
            ))
        .from(thread)
        .leftJoin(account).on(thread.account.id.eq(account.id))
        .leftJoin(category).on(thread.category.id.eq(category.id))
        .leftJoin(accountImage).on(accountImage.account.id.eq(thread.account.id))
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

    JPQLQuery<Tuple> query = jpaQueryFactory
        .select(
            thread.id,
            thread.title,
            category.name,
            thread.status,
            JPAExpressions
                .select(Wildcard.count)
                .from(comment)
                .where(comment.thread.id.eq(thread.id)),
            ExpressionUtils.as(
                JPAExpressions
                    .select(Wildcard.count)
                    .from(threadRecommend)
                    .where(threadRecommend.thread.id.eq(thread.id)), "likeCount"),
            account.nickname,
            thread.createdDate,
            thread.endDate,
            accountImage.imageUrl
        )
        .from(thread)
        .leftJoin(thread.account, account)
        .leftJoin(thread.category, category)
        .leftJoin(accountImage).on(thread.account.id.eq(accountImage.account.id))
        .where(
            threadStatusEq(ThreadLocation.CONTINUE),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .groupBy(thread.id, accountImage.imageUrl)
        .orderBy(orderByCondSort(cond.getSort()), orderByCondSort(Sort.NEW))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Tuple> tuples = query.fetch();

    List<ThreadSimpleResponseDto> dtos = tuples.stream()
        .map(tuple -> new ThreadSimpleResponseDto(
            tuple.get(thread.id),
            tuple.get(thread.title),
            tuple.get(category.name),
            tuple.get(thread.status),
            tuple.get(4, Long.class),
            tuple.get(5, Long.class),
            tuple.get(account.nickname),
            tuple.get(thread.createdDate),
            tuple.get(thread.endDate),
            tuple.get(accountImage.imageUrl)
        ))
        .collect(Collectors.toList());

    long total = query.fetchCount();

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }

  @Override
  public MyPage<ThreadSimpleResponseDto> selectArchivedThreadWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPQLQuery<Tuple> query = jpaQueryFactory
        .select(
            thread.id,
            thread.title,
            category.name,
            thread.status,
            JPAExpressions
                .select(Wildcard.count)
                .from(comment)
                .where(comment.thread.id.eq(thread.id)),
            ExpressionUtils.as(
                JPAExpressions
                    .select(Wildcard.count)
                    .from(threadRecommend)
                    .where(threadRecommend.thread.id.eq(thread.id)), "likeCount"),
            account.nickname,
            thread.createdDate,
            thread.endDate,
            accountImage.imageUrl
        )
        .from(thread)
        .leftJoin(thread.account, account)
        .leftJoin(thread.category, category)
        .leftJoin(accountImage).on(thread.account.id.eq(accountImage.account.id))
        .where(
            threadStatusEq(ThreadLocation.ARCHIVED),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond)
        )
        .groupBy(thread.id, accountImage.imageUrl)
        .orderBy(orderByCondSort(cond.getSort()), orderByCondSort(Sort.NEW))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Tuple> tuples = query.fetch();

    List<ThreadSimpleResponseDto> dtos = tuples.stream()
        .map(tuple -> new ThreadSimpleResponseDto(
            tuple.get(thread.id),
            tuple.get(thread.title),
            tuple.get(category.name),
            tuple.get(thread.status),
            tuple.get(4, Long.class),
            tuple.get(5, Long.class),
            tuple.get(account.nickname),
            tuple.get(thread.createdDate),
            tuple.get(thread.endDate),
            tuple.get(accountImage.imageUrl)
        ))
        .collect(Collectors.toList());

    long total = query.fetchCount();

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }

  private OrderSpecifier orderByCondSort(Sort sort) {
    if (sort.equals(Sort.NEW)) {
      return new OrderSpecifier<>(Order.DESC, thread.id);
    } else {
      return new OrderSpecifier<>(Order.DESC, Expressions.stringPath("likeCount"));
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
    return hasText(word) ? thread.title.containsIgnoreCase(word)
        .or(thread.content.containsIgnoreCase(word)) : null;
  }

  private BooleanExpression threadStatusEq(ThreadLocation location) {
    return thread.location.eq(location);
  }


  @Override
  public MyPage<ThreadsSimpleResponseDtoByAdmin> selectThreadByAdmin(ThreadSearchCondByAdmin cond,
      Pageable pageable) {

    final List<ThreadsSimpleResponseDtoByAdmin> fetch = jpaQueryFactory.select(
            new QThreadsSimpleResponseDtoByAdmin(thread.id,
                thread.title,
                thread.content,
                category.name,
                JPAExpressions
                    .select(Wildcard.count)
                    .from(threadRecommend)
                    .where(threadRecommend.thread.id.eq(thread.id)),
                account.nickname,
                thread.location))
        .from(thread)
        .leftJoin(thread.account, account)
        .leftJoin(thread.category, category)
        .where(
            threadStatusCheck(cond.getThreadStatus())
        )
        .orderBy(thread.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    final JPAQuery<Long> countThread = jpaQueryFactory.select(thread.count()).from(thread)
        .where(
            threadStatusCheck(cond.getThreadStatus())
        );
    return new MyPage<>(new PageImpl<>(fetch, pageable, countThread.fetchOne()));
  }

  private BooleanExpression threadStatusCheck(String threadStatus) {
    return hasText(threadStatus) ? thread.status.eq(ThreadStatus.valueOf(threadStatus)) : null;
  }

}

// ===============================================

/* N+1
 @Override
  public MyPage<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<Thread> query = query(thread, cond, ThreadLocation.CONTINUE)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Thread> threads = query.fetch();
    Long totalSize = countQuery(cond, ThreadLocation.CONTINUE).fetch().get(0);

    PageImpl<ThreadSimpleResponseDto> impl = new PageImpl<>(
        ThreadSimpleResponseDto.of(threads), pageable, totalSize);

    return new MyPage<>(impl);
  }

  private <T> JPAQuery<T> query(Expression<T> expr, ThreadSearchCond cond,
      ThreadLocation location) {
    return jpaQueryFactory
        .select(expr)
        .from(thread)
        .leftJoin(thread.recommends, threadRecommend)
        .leftJoin(thread.account, account)
        .leftJoin(thread.category, category)
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

/* Fetch

  @Override
  public MyPage<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<Thread> query = query(thread, cond, ThreadLocation.CONTINUE)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Thread> threads = query.fetch();
    Long totalSize = countQuery(cond, ThreadLocation.CONTINUE).fetch().get(0);

    PageImpl<ThreadSimpleResponseDto> impl = new PageImpl<>(
        ThreadSimpleResponseDto.of(threads), pageable, totalSize);

    return new MyPage<>(impl);
  }

  private <T> JPAQuery<T> query(Expression<T> expr, ThreadSearchCond cond,
      ThreadLocation location) {
    return jpaQueryFactory
        .select(expr)
        .from(thread)
        .leftJoin(thread.recommends, threadRecommend).fetchJoin().groupBy(threadRecommend.id)
        .leftJoin(thread.account, account).fetchJoin()
        .leftJoin(thread.category, category).fetchJoin()
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

/*my
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
        .leftJoin(category).on(thread.category.id.eq(category.id))
        .leftJoin(threadRecommend).on(threadRecommend.thread.id.eq(thread.id))
        .groupBy(thread.id)
        .where(
            threadStatusEq(ThreadLocation.CONTINUE),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond))
        .orderBy(
            orderByCondSort(cond.getSort()),
            orderByCondSort(Sort.NEW))
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
 */

/* 1
  @Override
  public MyPage<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPQLQuery<Tuple> query = jpaQueryFactory
        .select(thread.id, thread.title, category.name, comment.count(), threadRecommend.count(),
            account.nickname, thread.createdDate, thread.endDate)
        .from(thread)
        .leftJoin(thread.comments, comment).on(comment.thread.eq(thread))
        .leftJoin(thread.recommends, threadRecommend).on(threadRecommend.thread.eq(thread))
        .leftJoin(thread.account, account)
        .leftJoin(thread.category, category)
        .where(threadStatusEq(ThreadLocation.CONTINUE),
            titleOrContentContainsWord(cond.getWord()),
            categoryEq(cond))
        .groupBy(thread.id)
        .orderBy(orderByCondSort(cond.getSort()), orderByCondSort(Sort.NEW))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Tuple> tuples = query.fetch();

    List<ThreadSimpleResponseDto> dtos = tuples.stream()
        .map(tuple -> new ThreadSimpleResponseDto(
            tuple.get(thread.id),
            tuple.get(thread.title),
            tuple.get(category.name),
            tuple.get(comment.count()),
            tuple.get(threadRecommend.count()),
            tuple.get(account.nickname),
            tuple.get(thread.createdDate),
            tuple.get(thread.endDate)))
        .collect(Collectors.toList());

    long total = query.fetchCount();

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }

 */

/* 2
@Override
public MyPage<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
  Pageable pageable = cond.getPageable();

  JPQLQuery<Tuple> query = jpaQueryFactory
      .select(
          thread.id,
          thread.title,
          category.name,
          comment.count(),
          threadRecommend.count(),
          account.nickname,
          thread.createdDate,
          thread.endDate
      )
      .from(thread)
      .leftJoin(thread.comments, comment)
      .leftJoin(thread.recommends, threadRecommend)
      .leftJoin(thread.account, account)
      .leftJoin(thread.category, category)
      .where(
          threadStatusEq(ThreadLocation.CONTINUE),
          titleOrContentContainsWord(cond.getWord()),
          categoryEq(cond)
      )
      .groupBy(thread.id)
      .orderBy(orderByCondSort(cond.getSort()), orderByCondSort(Sort.NEW))
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize());

  List<Tuple> tuples = query.fetch();

  List<ThreadSimpleResponseDto> dtos = tuples.stream()
      .map(tuple -> new ThreadSimpleResponseDto(
          tuple.get(thread.id),
          tuple.get(thread.title),
          tuple.get(category.name),
          tuple.get(comment.count()),
          tuple.get(threadRecommend.count()),
          tuple.get(account.nickname),
          tuple.get(thread.createdDate),
          tuple.get(thread.endDate)
      ))
      .collect(Collectors.toList());

  long total = query.fetchCount();

  return new MyPage<>(new PageImpl<>(dtos, pageable, total));
}

 */

    /*
    SELECT c.opinion, a.gender, count(*)
    FROM comment as c
    LEFT JOIN account AS a ON a.id = c.account_id
    WHERE c.thread_id = 2
    GROUP BY a.gender, c.opinion
    */

    /*
    SELECT c.opinion, a.age, count(*)
    FROM comment as c
    LEFT JOIN account AS a ON a.id = c.account_id
    WHERE c.thread_id = 2
    GROUP BY a.age, c.opinion
    ORDER BY a.age
    */