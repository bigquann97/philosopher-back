package gladiator.philosopher.thread.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.recommend.entity.Recommend;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thread extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  private String content;

  private LocalDateTime endDate;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ThreadLocation location;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ThreadStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
  private List<ThreadImage> threadImages = new ArrayList<>();

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
  private List<Recommend> recommends = new ArrayList<>();

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
  private List<ThreadOpinion> opinions = new ArrayList<>();

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

  public List<Recommend> getRecommends() {
    return new ArrayList<>(this.recommends);
  }
}
