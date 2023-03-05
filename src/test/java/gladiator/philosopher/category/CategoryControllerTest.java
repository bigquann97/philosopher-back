package gladiator.philosopher.category;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gladiator.philosopher.category.controller.CategoryController;
import gladiator.philosopher.category.dto.CategoryRequestDto;
import gladiator.philosopher.category.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

  @InjectMocks
  CategoryController categoryController;

  @Mock
  CategoryService categoryService;

  MockMvc mockMvc;

  ObjectMapper om = new ObjectMapper();

  @BeforeEach
  void beforeEach() {
    mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
  }

  @DisplayName("1. 전체 카테고리 조회")
  @Test
  void test() throws Exception {
    // given, when
    mockMvc
        .perform(
            get("/api/categories"))

        // then
        .andExpect(
            status().isOk());

    Mockito.verify(categoryService).selectAllCategories();
  }

//  @DisplayName("2. 카테고리 생성")
//  @Test
//  void test_2() throws Exception {
//    // given
//    CategoryRequestDto dto = new CategoryRequestDto("철학");
//
//    mockMvc
//        .perform(
//            post("/api/categories")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(om.writeValueAsString(dto)))
//        .andExpect(
//            status().isCreated());
//
//    Mockito.verify(categoryService).createCategory(any(CategoryRequestDto.class));
//  }

  @DisplayName("3. 카테고리 삭제")
  @Test
  void test_3() throws Exception {
    // given
    Long id = 1L;

    // when, then
    mockMvc
        .perform(
            delete("/api/categories/{id}", id))
        .andExpect(status().isOk());

    Mockito.verify(categoryService).deleteCategory(id);
  }

  @DisplayName("4. 카테고리 수정")
  @Test
  void test_4() throws Exception {
    // given
    CategoryRequestDto dto = new CategoryRequestDto("철학");
    Long id = 1L;

    // when, then
    mockMvc
        .perform(
            put("/api/categories/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(dto)))
        .andExpect(status().isOk());

    Mockito.verify(categoryService).modifyCategory(any(Long.class), any(CategoryRequestDto.class));
  }


}