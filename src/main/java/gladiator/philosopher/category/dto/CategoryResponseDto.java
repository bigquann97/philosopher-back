package gladiator.philosopher.category.dto;

import gladiator.philosopher.category.entity.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CategoryResponseDto {

  private final Long id;

  private final String name;

  public static CategoryResponseDto of(final Category entity) {
    return new CategoryResponseDto(entity.getId(), entity.getName());
  }
  
}
