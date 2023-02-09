package gladiator.philosopher.thread.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.BaseEntity;
import gladiator.philosopher.post.entity.PostImage;
import java.time.LocalDateTime;
import java.util.List;
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

  @OneToMany(mappedBy = "post")
  private List<PostImage> postImages;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ThreadStatus status;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  @Builder
  public Thread(String title, String content, List<PostImage> postImages, Account account,
      LocalDateTime startTime, LocalDateTime endTime) {
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
}
