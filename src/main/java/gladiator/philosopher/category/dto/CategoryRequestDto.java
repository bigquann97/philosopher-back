package gladiator.philosopher.category.dto;

import gladiator.philosopher.category.entity.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class CategoryRequestDto {

  private final String name;

  public Category toEntity() {
    return new Category(this.name);
  }

  public CategoryRequestDto(String name) {
    this.name = name;
  }

}
