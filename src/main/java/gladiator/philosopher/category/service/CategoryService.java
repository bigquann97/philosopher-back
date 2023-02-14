package gladiator.philosopher.category.service;

import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.dto.CategoryResponseDto;
import gladiator.philosopher.category.entity.Category;
import java.util.List;

public interface CategoryService {

  void createCategory(CategoryRequestDto dto);

  List<CategoryResponseDto> selectAllCategories();

  void deleteCategory(Long id);

  void modifyCategory(Long id, CategoryRequestDto dto);

  Category getCategoryEntity(Long id);
}
