package gladiator.philosopher.report.repository.thread;

import static gladiator.philosopher.report.entity.QThreadReport.threadReport;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import gladiator.philosopher.report.enums.ReportCategory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

public class ThreadReportRepositoryImpl implements ThreadReportCustomRepository {

  public final JPAQueryFactory jpaQueryFactory;

  public ThreadReportRepositoryImpl(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public MyPage<ThreadReportResponseDto> getThreadReports(ThreadReportSearchCondition condition,
      Pageable pageable) {
    final List<ThreadReportResponseDto> fetch = jpaQueryFactory.select(
            Projections.constructor(ThreadReportResponseDto.class,
                threadReport.id,
                threadReport.content,
                threadReport.category,
                threadReport.reporter.nickname,
                threadReport.threadId
            )

        ).from(threadReport)
        .where(
            threadReportCategoryEqual(condition.getReportCategory())
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(threadReport.id.desc())
        .fetch();

    final JPAQuery<Long> count = jpaQueryFactory.select(threadReport.count())
        .from(threadReport)
        .where(
            threadReportCategoryEqual(condition.getReportCategory())
        );
    return new MyPage<>(new PageImpl<>(fetch, pageable, count.fetchOne()));
  }

  private BooleanExpression threadReportCategoryEqual(ReportCategory reportCategory) {
    if (ObjectUtils.isEmpty(reportCategory)) {
      return null;
    }
    return threadReport.category.eq(reportCategory);
  }

}