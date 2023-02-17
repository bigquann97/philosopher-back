package gladiator.philosopher.thread.repository;

import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.recommend.entity.QThreadRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.QThread;
import gladiator.philosopher.thread.entity.Sort;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadLocation;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
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
  private final QThreadRecommend threadRecommend;

  public ThreadCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.thread = QThread.thread;
    this.threadRecommend = QThreadRecommend.threadRecommend;
  }

  @Override
  public Optional<ThreadResponseDto> selectThread(Long id) {
    Thread resultThread = jpaQueryFactory
        .select(thread)
        .from(thread)
        .leftJoin(thread.threadImages).fetchJoin()
        .leftJoin(thread.recommends).fetchJoin()
        .leftJoin(thread.account).fetchJoin()
        .leftJoin(thread.opinions).fetchJoin()
        .where(thread.id.eq(id))
        .fetchOne();
    System.out.println(resultThread);
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
  @Transactional
  public Page<ThreadSimpleResponseDto> selectArchivedThreadWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    JPAQuery<Thread> query = query(thread, cond, ThreadLocation.ARCHIVED)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Thread> threads = query.fetch();
    Long totalSize = countQuery(cond, ThreadLocation.ARCHIVED).fetch().get(0);

    return new PageImpl<>(ThreadSimpleResponseDto.of(threads), pageable, totalSize);
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