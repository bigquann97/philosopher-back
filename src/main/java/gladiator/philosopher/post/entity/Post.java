package gladiator.philosopher.post.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostStatus;
import gladiator.philosopher.recommend.entity.PostRecommend;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(nullable = false)
  private String title;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostImage> images = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostRecommend> recommends = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostOpinion> opinions = new ArrayList<>();

  @Column(nullable = false)
  private String content;

  @Enumerated(EnumType.STRING)
  private PostStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  private Category category;

  private boolean isThreaded;

  @Builder
  public Post(Account account, String title, String content, List<PostImage> images,
      List<PostOpinion> opinions, Category category) {
    this.account = account;
    this.title = title;
    this.content = content;
    this.status = PostStatus.ACTIVE;
    this.images = images;
    this.opinions = opinions;
    this.category = category;
    this.isThreaded = false;
  }

  // 테스트 생성자
  public Post(Account account, String title, String content, List<PostOpinion> opinions,
      Category category) {
    this.account = account;
    this.title = title;
    this.content = content;
    this.status = PostStatus.ACTIVE;
    this.opinions = opinions;
    this.category = category;
    this.isThreaded = false;
  }

  public void modifyPost(String title, String content ) {
    this.title = title;
    this.content = content;
  }

  public void blind() {
    this.status = PostStatus.BLINDED;
  }

  public void releaseBlind() {
    this.status = PostStatus.ACTIVE;
  }

  public void isWriter(Account account) {
    if (this.account.equals(account)) {
    } else {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
  }

  public void makeThread() {
    this.isThreaded = true;
  }

  public boolean isBlinded() {
    return this.status == PostStatus.BLINDED;
  }


}
