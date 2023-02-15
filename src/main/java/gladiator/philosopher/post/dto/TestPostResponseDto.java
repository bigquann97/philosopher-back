package gladiator.philosopher.post.dto;

import gladiator.philosopher.account.entity.Account;
import lombok.Data;
import lombok.Getter;

@Data
public class TestPostResponseDto {

  private Long id;
  private String title;
  private String content;
  private String nickname;
//  private Long recommend;


  public TestPostResponseDto(Long id, String title, String content, String nickname) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
//    this.recommend = recommend;
  }
//  public TestPostResponseDto(){}
}
