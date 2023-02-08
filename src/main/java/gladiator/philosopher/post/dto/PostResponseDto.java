package gladiator.philosopher.post.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PostResponseDto {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String nickname;

  @Column
  private String image;

  @Column(nullable = false)
  private String content;

  public PostResponseDto(Post post) {
    this.title = post.getTitle();
    this.nickname = post.getNickname();
    this.image = post.getImage();
    this.content = post.getContent();
  }
}
