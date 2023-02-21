package gladiator.philosopher.category.controller;

import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.dto.CategoryResponseDto;
import gladiator.philosopher.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

  private final CategoryService categoryService;

  /**
   * 카테고리 생성
   *
   * @param dto
   */
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createCategory(final CategoryRequestDto dto) {
    categoryService.createCategory(dto);
  }

  /**
   * 카테고리 선택
   *
   * @return
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<CategoryResponseDto> selectCategories() {
    return categoryService.selectAllCategories();
  }

  /**
   * 카테고리 삭제
   *
   * @param categoryId
   */
  @DeleteMapping("/{categoryId}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteCategory(final @PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
  }

  /**
   * 카테고리 수정
   *
   * @param categoryId
   * @param dto
   */
  @PutMapping("/{categoryId}")
  @ResponseStatus(HttpStatus.OK)
  public void modifyCategory(
      final @PathVariable Long categoryId,
      final CategoryRequestDto dto
  ) {
    categoryService.modifyCategory(categoryId, dto);
  }


}
