package gladiator.philosopher.thread.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.post.entity.QPostImage;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.recommend.entity.QRecommend;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.QThread;
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
  private final QPostImage postImage;
  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;

  public ThreadCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory,
      PostRepository postRepository,
      PostImageRepository postImageRepository) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.thread = QThread.thread;
    this.recommend = QRecommend.recommend;
    this.account = QAccount.account;
    this.postImage = QPostImage.postImage;
    this.postRepository = postRepository;
    this.postImageRepository = postImageRepository;
  }

//  @Override
//  public Page<Thread> getThreads(ThreadSearchCond cond) {
//    Pageable pageable = cond.getPageable();
//    List<Thread> result = jpaQueryFactory.select(thread)
//        .from(thread)
//        .leftJoin(thread.postImages, postImage)
//        .leftJoin(thread.recommends, recommend)
//        .leftJoin(thread.account, account)
//        .groupBy(thread.id)
//        .offset(pageable.getOffset())
//        .limit(pageable.getPageSize()).fetch();
//
//    return new PageImpl<>(result);
//  }

  public Page<ThreadResponseDto> searchList(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();
    return null;
  }

  @Override
  public Optional<ThreadResponseDto> selectThread(Long id) {

    List<String> images = jpaQueryFactory.select(postImage.uniqueName).from(postImage)
        .where(postImage.thread.id.eq(id)).fetch();

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
        .leftJoin(thread.postImages, postImage)
        .leftJoin(thread.recommends, recommend)
        .leftJoin(thread.account, account)
        .groupBy(thread.id)
        .where(thread.id.eq(id))
        .orderBy(thread.id.desc())
        .fetchOne();

    dto.addImage(images);
    return Optional.ofNullable(dto);
  }

  @Override
  public Page<ThreadSimpleResponseDto> selectActiveThreadsWithPaging(ThreadSearchCond cond) {
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
        .where(thread.status.eq(ThreadLocation.CONTINUE))
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(thread.id.desc())
        .fetch();

    return new PageImpl<>(dtos, pageable, dtos.size());
  }

  @Override
  public Page<ThreadResponseDto> getThreadsByCondition(ThreadSearchCond cond) {
    return null;
  }

  @Override
  public Page<ThreadSimpleResponseDto> selectArchivedThreadsWithPaging(ThreadSearchCond cond) {
    Pageable pageable = cond.getPageable();

    List<ThreadSimpleResponseDto> responseDtoList = jpaQueryFactory
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
        .where(thread.status.eq(ThreadLocation.ARCHIVED))
        .groupBy(thread.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(thread.id.desc())
        .fetch();

    return new PageImpl<>(responseDtoList, pageable, responseDtoList.size());
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
