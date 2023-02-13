package gladiator.philosopher.post.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
  private List<PostImage> images;

  @Column(nullable = false)
  private String content;

  @Builder
  public Post(Account account, String title, String content, List<PostImage> images) {
    this.account = account;
    this.title = title;
    this.content = content;
    this.images = images;
  }

  public Post(Account account, String title, String content) {
    this.account = account;
    this.title = title;
    this.content = content;
  }

  public void modifyPost(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
  }

  public void blind() {

  }

  public boolean isWriter(AccountDetails memberDetails) {
    if (this.account.getEmail().equals(memberDetails.getUsername())) {
      return true;
    } else {
      return false;
    }
  }

}
