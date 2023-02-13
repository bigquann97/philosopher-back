package gladiator.philosopher.thread.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.recommend.entity.Recommend;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
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

  @OneToMany(mappedBy = "thread")
  private List<PostImage> postImages;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ThreadStatus status;

  private LocalDateTime endTime;

  @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
  private Set<Recommend> recommends = new LinkedHashSet<>();

  @Builder
  public Thread(String title, String content, List<PostImage> postImages, Account account,
      LocalDateTime endTime) {
    this.title = title;
    this.content = content;
    this.postImages = postImages;
    this.account = account;
    this.status = ThreadStatus.CONTINUE;
    this.endTime = endTime;
  }

  public Thread finishThread() {
    this.status = ThreadStatus.ARCHIVED;
    return this;
  }

  public void blind() {

  }
}
