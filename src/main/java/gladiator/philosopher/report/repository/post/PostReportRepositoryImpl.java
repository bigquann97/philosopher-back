package gladiator.philosopher.report.repository.post;

import static gladiator.philosopher.report.entity.QPostReport.postReport;
import static gladiator.philosopher.report.entity.QThreadReport.threadReport;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.PostReportSearchCondition;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import gladiator.philosopher.report.enums.ReportCategory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

public class PostReportRepositoryImpl implements PostReportCustomRepository{

  public final JPAQueryFactory jpaQueryFactory;

  public PostReportRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public MyPage<PostReportResponseDto> getPostReports(PostReportSearchCondition condition,
      Pageable pageable) {
    final List<PostReportResponseDto> fetch = jpaQueryFactory.select(
            Projections.constructor(PostReportResponseDto.class,
                postReport.id,
                postReport.content,
                postReport.category,
                postReport.reporter.nickname,
                postReport.postId
            ))
        .from(postReport)
        .where(postReportCategoryEqual(condition.getReportCategory()))
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(postReport.id.desc())
        .fetch();

    final JPAQuery<Long> count = jpaQueryFactory.select(threadReport.count())
        .from(threadReport)
        .where(postReportCategoryEqual(condition.getReportCategory()));

    return new MyPage<>(new PageImpl<>(fetch, pageable, count.fetchOne()));
  }

  private BooleanExpression postReportCategoryEqual(ReportCategory reportCategory) {
    if(ObjectUtils.isEmpty(reportCategory)) {
      return null;
    }return threadReport.category.eq(reportCategory);
  }

}
