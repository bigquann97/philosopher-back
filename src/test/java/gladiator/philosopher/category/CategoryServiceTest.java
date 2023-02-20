package gladiator.philosopher.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.dto.CategoryResponseDto;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.repository.CategoryRepository;
import gladiator.philosopher.category.service.CategoryServiceImpl;
import gladiator.philosopher.factory.CategoryFactory;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @InjectMocks
  CategoryServiceImpl categoryService;

  @Mock
  CategoryRepository categoryRepository;

  @DisplayName("1. selectAllCategories")
  @Test
  void test_1() {
    // given
    Category category1 = CategoryFactory.createCategory1();
    Category category2 = CategoryFactory.createCategory2();
    List<Category> categories = List.of(category1, category2);
    given(categoryRepository.findAll()).willReturn(categories);

    // when
    List<CategoryResponseDto> result = categoryService.selectAllCategories();

    // then
    Assertions.assertThat(result.size()).isEqualTo(2);
    Assertions.assertThat(result.get(0).getName()).isEqualTo(category1.getName());
    Assertions.assertThat(result.get(1).getName()).isEqualTo(category2.getName());
  }

  @DisplayName("2. createCategory")
  @Test
  void test_2() {
    // given
    CategoryRequestDto dto = new CategoryRequestDto("철학");

    // when
    categoryService.createCategory(dto);

    // then
    Mockito.verify(categoryRepository).save(any(Category.class));
  }

  @DisplayName("3. deleteCategory")
  @Test
  void test_3() {
    // given
    Long id = 1L;

    // when
    categoryService.deleteCategory(id);

    // then
    Mockito.verify(categoryRepository).deleteById(id);
  }

  @DisplayName("4. modifyCategory")
  @Test
  void test_4() {
    // given
    Long id = 1L;
    CategoryRequestDto req = new CategoryRequestDto("철학");
    Category category = CategoryFactory.createCategory1();
    when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

    // when
    categoryService.modifyCategory(id, req);

    Mockito.verify(categoryRepository).save(any(Category.class));
  }

  @DisplayName("5. getCategoryEntity")
  @Test
  void test_5() {
    // given
    Category category = CategoryFactory.createCategory1();
    when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
    when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

    // when
    Optional<Category> category1 = categoryRepository.findById(1L);
    Optional<Category> category2 = categoryRepository.findById(2L);

    // then
    Assertions.assertThatNoException().isThrownBy(category1::get);
    Assertions.assertThat(category2).isEmpty();
  }


}