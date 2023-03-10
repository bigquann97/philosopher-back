package gladiator.philosopher.thread.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.thread.enums.ThreadLocation;
import gladiator.philosopher.thread.enums.ThreadStatus;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thread extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Lob
  private String content;

  private LocalDateTime endDate;

  @Enumerated(EnumType.STRING)
  private ThreadLocation location;

  @Enumerated(EnumType.STRING)
  private ThreadStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @Builder
  public Thread(String title, String content, Account account, LocalDateTime endDate,
      Category category) {
    this.title = title;
    this.content = content;
    this.account = account;
    this.endDate = endDate;
    this.category = category;
    this.status = ThreadStatus.ACTIVE;
    this.location = ThreadLocation.CONTINUE;
  }

  public Thread finishThread() {
    this.location = ThreadLocation.ARCHIVED;
    return this;
  }

  public void blind() {
    this.status = ThreadStatus.BLINDED;
  }

  public void releaseBlind() {
    this.status = ThreadStatus.ACTIVE;
  }

  public boolean isArchived() {
    return this.location == ThreadLocation.ARCHIVED;
  }

  public boolean isBlinded() {
    return this.status == ThreadStatus.BLINDED;
  }


  public void modifyThread(String title, String content, Category category) {
    this.title = title;
    this.content = content;
    this.category = category;
  }

  public Thread modifyThreadStatusByAdmin(ThreadStatus threadStatus) {
    this.status = threadStatus;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Thread thread = (Thread) o;
    return id != null && Objects.equals(id, thread.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public boolean isDeleted() {
    return this.status == ThreadStatus.DELETED;
  }
}
