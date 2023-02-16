package gladiator.philosopher.post.dto;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class PostRequestDto {

  private final String title;
  private final String content;
  private final List<String> opinions;
  private final Long category;

  public PostRequestDto(String title, String content, List<String> opinions, Long category) {
    this.title = title;
    this.content = content;
    this.opinions = opinions;
    this.category = category;
  }
  
  public Post toEntity(Account account, Category category) {
    return Post.builder()
        .account(account)
        .title(this.title)
        .content(this.content)
        .category(category)
        .build();
  }

  public void checkByOpinionCount() {
    if (this.getOpinions().size() > 5) {
      throw new CustomException(ExceptionStatus.TO_MUCH_INPUTDATAS);
    }
  }
}
