package gladiator.philosopher.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.QComment;
import gladiator.philosopher.comment.entity.QMention;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.recommend.entity.QCommentRecommend;
import gladiator.philosopher.thread.entity.QThread;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

public class CommentCustomRepositoryImpl extends QuerydslRepositorySupport implements
    CommentCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QComment comment;
  private final QAccount account;
  private final QCommentRecommend commentRecommend;
  private final QMention mention;
  private final QThread thread;

  public CommentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.comment = QComment.comment;
    this.account = QAccount.account;
    this.commentRecommend = QCommentRecommend.commentRecommend;
    this.mention = QMention.mention;
    this.thread = QThread.thread;
  }

  @Override
  @Transactional
  public MyPage<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long threadId) {
    List<CommentResponseDto> dtos = jpaQueryFactory
        .select(
            Projections.constructor(CommentResponseDto.class, comment,
                JPAExpressions.select(commentRecommend.count())
                    .from(commentRecommend)
                    .where(commentRecommend.comment.id.eq(comment.id))))
        .from(comment)
        .leftJoin(comment.thread, thread).fetchJoin()
        .leftJoin(comment.account, account).fetchJoin()
        .where(comment.thread.id.eq(threadId))
        .orderBy(comment.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = jpaQueryFactory
        .select(comment.count())
        .from(comment)
        .where(comment.thread.id.eq(threadId))
        .fetchOne();

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }

}
/* 1
  private final JPAQueryFactory jpaQueryFactory;
  private final QComment comment;

  public CommentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.comment = QComment.comment;
  }

  @Override
  @Transactional
  public Page<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long threadId) {
    JPAQuery<Comment> query = query(comment, threadId)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize());

    List<Comment> comments = query.fetch();
    Long totalSize = countQuery(threadId).fetch().get(0);

    return PageableExecutionUtils.getPage(CommentResponseDto.of(comments), pageable,
        () -> totalSize);
  }

  private <T> JPAQuery<T> query(Expression<T> expr, Long threadId) {
    return jpaQueryFactory
        .select(expr)
        .from(comment)
        .leftJoin(comment.account).fetchJoin()
        .leftJoin(comment.mentionings).fetchJoin()
        .leftJoin(comment.mentioneds).fetchJoin()
        .leftJoin(comment.recommends).fetchJoin()
        .leftJoin(comment.thread).fetchJoin()
        .where(comment.thread.id.eq(threadId))
        .orderBy(comment.id.desc());
  }

  private JPAQuery<Long> countQuery(Long threadId) {
    return jpaQueryFactory
        .select(Wildcard.count)
        .from(comment)
        .where(comment.thread.id.eq(threadId))
        .groupBy(comment.id)
        .orderBy(comment.id.desc());
  }


 */

/* my
  @Override
  @Transactional
  public MyPage<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long threadId) {
    List<Tuple> tupleList = jpaQueryFactory
        .select(
            comment,
            JPAExpressions
                .select(count)
                .from(commentRecommend)
                .where(commentRecommend.comment.id.eq(comment.id)))
        .from(comment)
        .leftJoin(comment.mentionings).fetchJoin()
        .leftJoin(comment.mentioneds).fetchJoin()
        .where(comment.thread.id.eq(threadId))
        .orderBy(comment.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    List<CommentResponseDto> dtos = new ArrayList<>();

    for (Tuple tuple : tupleList) {
      Comment comment = tuple.get(0, Comment.class);
      Long recommendCount = tuple.get(1, Long.class);
      dtos.add(CommentResponseDto.of(comment, recommendCount));
    }

    Long total = jpaQueryFactory
        .select(count)
        .from(comment)
        .where(comment.thread.id.eq(threadId))
        .fetch().get(0);

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }
 */

/*2
  @Override
  @Transactional
  public MyPage<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long threadId) {
    List<CommentResponseDto> dtos = jpaQueryFactory
        .select(
            Projections.constructor(CommentResponseDto.class, comment,
                JPAExpressions.select(commentRecommend.count())
                    .from(commentRecommend)
                    .where(commentRecommend.comment.id.eq(comment.id))))
        .from(comment)
        .where(comment.thread.id.eq(threadId))
        .orderBy(comment.id.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    Long total = jpaQueryFactory
        .select(comment.count())
        .from(comment)
        .where(comment.thread.id.eq(threadId))
        .fetchOne();

    return new MyPage<>(new PageImpl<>(dtos, pageable, total));
  }
 */