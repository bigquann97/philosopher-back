package gladiator.philosopher.thread.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.recommend.entity.QRecommend;
import gladiator.philosopher.thread.dto.Sort;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.QThread;
import gladiator.philosopher.thread.entity.QThreadImage;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadLocation;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
  private final QThreadImage threadImage;

  public ThreadCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.thread = QThread.thread;
    this.recommend = QRecommend.recommend;
    this.account = QAccount.account;
    this.threadImage = QThreadImage.threadImage;
  }

  @Override
  public Optional<ThreadResponseDto> selectThread(Long id) {
    List<String> images = jpaQueryFactory
        .select(threadImage.imageUrl)
        .from(threadImage)
        .where(threadImage.thread.id.eq(id))
        .fetch();

    ThreadResponseDto dto = jpaQueryFactory
        .select(Projections.constructor(ThreadResponseDto.class,
            thread.id,
            thread.title,
            thread.content,
            thread.createdDate,
            thread.endDate,
            recommend.count(),
            account.nickname
        ))
        .from(thread)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .groupBy(thread.id)
        .where(thread.id.eq(id))
        .fetchOne();

    if (dto != null) {
      dto.addImage(images);
      return Optional.of(dto);
    } else {
      return Optional.empty();
    }
  }

  @Override
  public Page<ThreadSimpleResponseDto> selectActiveThreadsWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<ThreadSimpleResponseDto> dtos = jpaQueryFactory
        .select(Projections.constructor(
            ThreadSimpleResponseDto.class,
            thread.id,
            thread.title,
            recommend.count(),
            account.nickname,
            thread.createdDate,
            thread.endDate
        ))
        .from(thread)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .where(
            threadStatusEq(ThreadLocation.CONTINUE),
            titleContainsWord(cond.getWord())
                .or(contentContainsWord(cond.getWord())),
            categoryEq(cond)
        )
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(
            orderByCondSort(cond)
        )
        .fetch();

    Long total = jpaQueryFactory
        .select(Wildcard.count)
        .from(thread)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .where(
            threadStatusEq(ThreadLocation.CONTINUE),
            titleContainsWord(cond.getWord())
                .or(contentContainsWord(cond.getWord())),
            categoryEq(cond)
        )
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(
            orderByCondSort(cond)
        )
        .fetch().get(0);

    return new PageImpl<>(dtos, pageable, total);
  }

  @Override
  public Page<ThreadSimpleResponseDto> selectArchivedThreadWithCond(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<ThreadSimpleResponseDto> dtos = jpaQueryFactory
        .select(Projections.constructor(
            ThreadSimpleResponseDto.class,
            thread.id,
            thread.title,
            recommend.count(),
            account.nickname,
            thread.createdDate,
            thread.endDate
        ))
        .from(thread)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .where(
            threadStatusEq(ThreadLocation.ARCHIVED),
            titleContainsWord(cond.getWord())
                .or(contentContainsWord(cond.getWord())),
            categoryEq(cond)
        )
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(
            orderByCondSort(cond)
        )
        .fetch();

    Long total = jpaQueryFactory
        .select(Wildcard.count)
        .from(thread)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .where(
            threadStatusEq(ThreadLocation.ARCHIVED),
            titleContainsWord(cond.getWord())
                .or(contentContainsWord(cond.getWord())),
            categoryEq(cond)
        )
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(
            orderByCondSort(cond)
        )
        .fetch().get(0);

    return new PageImpl<>(dtos, pageable, total);
  }

  private OrderSpecifier<Long> orderByCondSort(ThreadSearchCond sort) {
    if (sort.getSort().equals(Sort.NEW)) {
      return thread.id.desc();
    } else {
      return recommend.count().desc();
    }
  }

  private BooleanExpression categoryEq(ThreadSearchCond cond) {
    if (cond.getCategoryId() == 0) {
      return null; //
    } else {
      return thread.category.id.eq(cond.getCategoryId());
    }
  }

  private BooleanExpression contentContainsWord(String word) {
    return thread.content.containsIgnoreCase(word);
  }

  private BooleanExpression titleContainsWord(String word) {
    return thread.title.containsIgnoreCase(word);
  }

  private BooleanExpression threadStatusEq(ThreadLocation location) {
    return thread.location.eq(location);
  }


  private Predicate searchByContent(String word) {
    return Objects.nonNull(word) ? thread.content.containsIgnoreCase(word) : null;
  }

  private BooleanExpression searchByTitle(String word) {
    return Objects.nonNull(word) ? thread.title.containsIgnoreCase(word) : null;
  }


}