package gladiator.philosopher.category.service;

import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.dto.CategoryResponseDto;
import gladiator.philosopher.category.entity.Category;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryService {

  void createCategory(final String name);

  List<CategoryResponseDto> selectAllCategories();

  void deleteCategory(final Long id);

  void modifyCategory(final Long id, final CategoryRequestDto dto);

  Category getCategoryEntity(final Long category);


}
