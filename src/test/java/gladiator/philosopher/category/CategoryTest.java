package gladiator.philosopher.category;

import static gladiator.philosopher.factory.CategoryFactory.createCategory1;
import static org.assertj.core.api.Assertions.assertThat;

import gladiator.philosopher.category.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

  @DisplayName("1. 카테고리 빌더")
  @Test
  void test_1() {
    // given, when
    Category category = Category.builder().name("name").build();

    // then
    assertThat(category.getName()).isEqualTo("name");
    assertThat(category.getId()).isEqualTo(null);
  }

  @DisplayName("2. Modify 카테고리")
  @Test
  void test_2() {
    // given
    Category category = createCategory1();
    String beforeName = category.getName();

    // when
    Category modifiedCategory = category.modifyName("modified");

    // then
    assertThat(modifiedCategory.getName()).isNotEqualTo(beforeName);
    assertThat(modifiedCategory.getName()).isEqualTo("modified");
  }

}