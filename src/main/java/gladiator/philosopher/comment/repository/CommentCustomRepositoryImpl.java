package gladiator.philosopher.comment.repository;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.QComment;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

public class CommentCustomRepositoryImpl extends QuerydslRepositorySupport implements
    CommentCustomRepository {

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


}
