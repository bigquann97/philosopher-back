package gladiator.philosopher.comment.repository;

import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.entity.QComment;
import gladiator.philosopher.mention.entity.QMention;
import gladiator.philosopher.recommend.entity.QRecommend;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

public class CommentCustomRepositoryImpl extends QuerydslRepositorySupport implements
    CommentCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final QComment comment;
  private final QAccount account;
  private final QRecommend recommend;
  private final QMention mention;

  public CommentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
    super(Thread.class);
    this.jpaQueryFactory = jpaQueryFactory;
    this.comment = QComment.comment;
    this.account = QAccount.account;
    this.recommend = QRecommend.recommend;
    this.mention = QMention.mention;
  }

  @Override
  @Transactional
  public Page<CommentResponseDto> selectCommentsWithPaging(Pageable pageable) {

    List<Comment> comments = jpaQueryFactory
        .selectFrom(comment)
        .join(comment.account, account)
        .leftJoin(comment.recommends, recommend)
        .leftJoin(comment.mentionings, mention)
        .leftJoin(comment.mentioneds, mention)
        .groupBy(comment.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(comment.id.desc())
        .fetch();

    Long count = jpaQueryFactory
        .select(Wildcard.count)
        .from(comment)
        .leftJoin(comment.account, account)
        .leftJoin(comment.recommends, recommend)
        .leftJoin(comment.mentionings, mention)
        .leftJoin(comment.mentioneds, mention)
        .groupBy(comment.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(comment.id.desc())
        .fetch().get(0);

    return new PageImpl<>(CommentResponseDto.of(comments), pageable, count);
  }


}
