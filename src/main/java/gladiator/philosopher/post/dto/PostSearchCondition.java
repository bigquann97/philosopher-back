package gladiator.philosopher.post.dto;

import lombok.Data;

@Data
public class PostSearchCondition {

  private String title;
  private String content;
  private String category;
  private String status;


}
