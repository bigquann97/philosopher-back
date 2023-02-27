package gladiator.philosopher.report.repository.comment;

import static gladiator.philosopher.report.entity.QCommentReport.commentReport;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.dto.CommentReportSearchCondition;
import gladiator.philosopher.report.enums.ReportCategory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

public class CommentReportRepositoryImpl implements CommentReportCustomRepository {

  public final JPAQueryFactory jpaQueryFactory;

  public CommentReportRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public MyPage<CommentReportResponseDto> getCommentReports(CommentReportSearchCondition condition,
      Pageable pageable) {
    final List<CommentReportResponseDto> fetch = jpaQueryFactory.select(
            Projections.constructor(CommentReportResponseDto.class,
                commentReport.id,
                commentReport.content,
                commentReport.category,
                commentReport.reporter.nickname,
                commentReport.commentId
            )

        ).from(commentReport)
        .where(
            commentReportCategoryEqual(condition.getReportCategory())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(commentReport.id.desc())
        .fetch();

    final JPAQuery<Long> count = jpaQueryFactory.select(commentReport.count())
        .from(commentReport)
        .where(
            commentReportCategoryEqual(condition.getReportCategory())
        );
    return new MyPage<>(new PageImpl<>(fetch, pageable, count.fetchOne()));
  }

  private BooleanExpression commentReportCategoryEqual(ReportCategory reportCategory) {
    if(ObjectUtils.isEmpty(reportCategory)){
      return null;
    }return commentReport.category.eq(reportCategory);
  }
}
