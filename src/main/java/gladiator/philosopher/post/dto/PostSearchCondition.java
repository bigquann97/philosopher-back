package gladiator.philosopher.post.dto;

import lombok.Data;

@Data
public class PostSearchCondition {

  // post -> 검색 관련해서 하나로 할 것인지, 따로 할 것인지.
  private String title;
  private String content;
  private String category;
  private String status;

}
