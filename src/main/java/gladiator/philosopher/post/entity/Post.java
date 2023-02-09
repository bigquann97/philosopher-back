package gladiator.philosopher.post.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.common.BaseEntity;
import gladiator.philosopher.post.dto.PostRequestDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(nullable = false)
  private String title;

  //  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//  private List<PostImages> images;
  @Column
  private String image;

  @Column(nullable = false)
  private String content;

  @Builder
  public Post(Account account, String title, String content) {
    this.account = account;
    this.title = title;
    this.content = content;
  }

  public void modifyPost(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
  }
}
