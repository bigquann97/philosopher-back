package gladiator.philosopher.admin.dto;

import lombok.Getter;

@Getter
public class ModifyThreadRequestDto {

  private String title;
  private String content;
  private Long categoryId;

  public ModifyThreadRequestDto(String title, String content, Long categoryId) {
    this.title = title;
    this.content = content;
    this.categoryId = categoryId;
  }
}
