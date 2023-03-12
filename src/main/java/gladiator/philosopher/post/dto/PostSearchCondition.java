package gladiator.philosopher.post.dto;

import lombok.Data;

@Data
public class PostSearchCondition {

  private String word;
  private Long categoryId;

//
//  @Builder
//  public PostSearchCondition(int page, Sort sort, String word, Pageable pageable, Long categoryId) {
//    this.page = page;
//    this.sort = sort;
//    this.categoryId = categoryId;
//    this.word = word;
//    this.pageable = pageable;
//  }
//
//  public static PostSearchCondition of(
//      Integer page,
//      Sort sort,
//      String word,
//      Long categoryId
//  ) {
//    Pageable pageable;
//
//    categoryId = (categoryId == null) || (categoryId <= 0) ? 0 : categoryId;
//    page = (page == null) || (page <= 0) ? 0 : page - 1;
//    sort = sort == null ? Sort.NEW : sort;
//    word = (word == null) ? "" : word;
//    org.springframework.data.domain.Sort sorted = org.springframework.data.domain.Sort.by(
//        Direction.DESC, sort.name());
//    pageable = PageRequest.of(page, 10, sorted);
//
//    return PostSearchCondition.builder()
//        .page(page)
//        .sort(sort)
//        .word(word)
//        .categoryId(categoryId)
//        .pageable(pageable)
//        .build();
//  }


}
