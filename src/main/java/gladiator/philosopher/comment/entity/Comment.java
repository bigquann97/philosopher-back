package gladiator.philosopher.comment.entity;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.common.BaseEntity;
import gladiator.philosopher.post.entity.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Comment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @ManyToOne
  @JoinColumn(name = "account_Id")
  private Account account;

  @ManyToOne
  @JoinColumn(name = "post_Id")
  private Post post;


  @Column(nullable = false)
  private String content;

  @Builder
  public Comment(Long id, Account account, Post post, String content) {
    this.id = id;
    this.account = account;
    this.post = post;
    this.content = content;
  }

  public void modifyComment(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public void blind() {

  }
}