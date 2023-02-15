package gladiator.philosopher.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.account.entity.QAccount;
import gladiator.philosopher.comment.dto.CommentResponseDto;
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

    List<CommentResponseDto> dtos = jpaQueryFactory
        .select(
            Projections.constructor(
                CommentResponseDto.class,
                comment.id,
                account.nickname,
                comment.opinion,
                comment.content,
                Projections.list(mention.mentioningComment.id),
                Projections.list(mention.mentionedComment.id),
                comment.createdDate,
                comment.status,
                recommend.count()
            )
        )
        .from(comment)
        .leftJoin(comment.account, account)
        .leftJoin(comment.mentionings, mention).on(mention.mentioningComment.id.eq(comment.id))
        .leftJoin(comment.mentioneds, mention).on(mention.mentionedComment.id.eq(comment.id))
        .leftJoin(comment.recommends, recommend)
        .groupBy(comment.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(comment.id.desc())
        .fetch();

    Long total = jpaQueryFactory
        .select(Wildcard.count)
        .from(comment)
        .leftJoin(comment.account, account)
        .leftJoin(comment.mentionings, mention).on(mention.mentioningComment.id.eq(comment.id))
        .leftJoin(comment.mentioneds, mention).on(mention.mentionedComment.id.eq(comment.id))
        .groupBy(comment.id)
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(comment.id.desc())
        .fetch().get(0);

    return new PageImpl<>(dtos, pageable, total);

  }


}
