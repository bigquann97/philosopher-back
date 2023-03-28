package gladiator.philosopher.report;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.factory.AccountFactory;
import gladiator.philosopher.report.entity.CommentReport;
import gladiator.philosopher.report.entity.PostReport;
import gladiator.philosopher.report.entity.Report;
import gladiator.philosopher.report.enums.ReportCategory;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReportTest {

  @Autowired
  private EntityManager entityManager;

  @Test
  void testReportEqualsAndHashCode() {
    Account reporter = AccountFactory.createUserAccount1();
    Account reported = AccountFactory.createUserAccount2();

    Report report1 = new PostReport("content", reporter, reported, ReportCategory.ADVERTISEMENT,
        1L);
    Report report2 = new PostReport("content", reporter, reported, ReportCategory.ADVERTISEMENT,
        1L);
    Report report3 = new CommentReport("content", reporter, reported, ReportCategory.ADVERTISEMENT,
        1L);
    Report report4 = new CommentReport("content", reporter, reported, ReportCategory.ADVERTISEMENT,
        1L);

    entityManager.persist(reporter);
    entityManager.persist(reported);
    entityManager.persist(report1);
    entityManager.persist(report2);
    entityManager.persist(report3);
    entityManager.persist(report4);
    entityManager.flush();
    entityManager.clear();

    assertEquals(report1, report2);
    assertEquals(report2, report1);
    assertNotEquals(report1, report3);
    assertNotEquals(report3, report1);
    assertNotEquals(report3, report4);
    assertNotEquals(report4, report3);
    assertEquals(report1.hashCode(), report2.hashCode());
    assertNotEquals(report1.hashCode(), report3.hashCode());
    assertNotEquals(report3.hashCode(), report4.hashCode());
  }
}