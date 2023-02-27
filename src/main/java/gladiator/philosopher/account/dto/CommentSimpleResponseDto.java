package gladiator.philosopher.account.dto;

import lombok.Getter;

@Getter
public class CommentSimpleResponseDto {

  private Long id;
  private String title; // thread의 title
  private String content; // thread의 댓글 ( 내가 어떤 댓글을 달았는지 )

  public CommentSimpleResponseDto(Long id, String title, String content) {
    this.id = id;
    this.title = title;
    this.content = content;
  }
}
