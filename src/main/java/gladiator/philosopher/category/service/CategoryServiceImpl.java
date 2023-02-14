package gladiator.philosopher.category.service;

import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.dto.CategoryResponseDto;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.repository.CategoryRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  @Transactional
  public void createCategory(final CategoryRequestDto dto) {
    Category category = dto.toEntity();
    categoryRepository.save(category);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CategoryResponseDto> selectAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categories.stream().map(CategoryResponseDto::of)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void deleteCategory(final Long id) {
    categoryRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void modifyCategory(final Long id, final CategoryRequestDto dto) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("카테고리 없음"));
    Category modifiedCategory = category.modifyName(dto.getName());
    categoryRepository.save(modifiedCategory);
  }

}
