package gladiator.philosopher.category.service;

import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.dto.CategoryResponseDto;
import gladiator.philosopher.category.entity.Category;
import java.util.List;

public interface CategoryService {

  void createCategory(final CategoryRequestDto dto);

  List<CategoryResponseDto> selectAllCategories();

  void deleteCategory(final Long id);

  void modifyCategory(final Long id, final CategoryRequestDto dto);

  Category getCategoryEntity(Long category);
}
