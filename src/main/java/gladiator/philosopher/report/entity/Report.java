package gladiator.philosopher.report.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.enums.ReportCategory;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_id", nullable = false)
  private Long id;

  @Lob
  protected String content;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  protected ReportCategory category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_account_id", nullable = false)
  protected Account reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reported_account_id", nullable = false)
  protected Account reported;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Report report = (Report) o;
    return id != null && Objects.equals(id, report.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
