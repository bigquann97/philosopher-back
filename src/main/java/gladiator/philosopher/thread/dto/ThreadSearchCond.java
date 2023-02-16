package gladiator.philosopher.thread.dto;

import gladiator.philosopher.thread.entity.Sort;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ThreadSearchCond {

  private final int page;
  private final Sort sort; // 정렬기준
  private final Long categoryId;
  private final String word; // 검색어
  private final Pageable pageable;

  @Builder
  public ThreadSearchCond(int page, Sort sort, String word, Pageable pageable, Long categoryId) {
    this.page = page;
    this.sort = sort;
    this.categoryId = categoryId;
    this.word = word;
    this.pageable = pageable;
  }

  public static ThreadSearchCond of(
      Integer page,
      Sort sort,
      String word,
      Long categoryId
  ) {
    Pageable pageable;

    categoryId = (categoryId == null) || (categoryId <= 0) ? 0 : categoryId;
    page = (page == null) || (page <= 0) ? 0 : page - 1;
    sort = sort == null ? Sort.NEW : sort;
    word = (word == null) ? "" : word;
    org.springframework.data.domain.Sort sorted = org.springframework.data.domain.Sort.by(
        Direction.DESC, sort.name());
    pageable = PageRequest.of(page, 10, sorted);

    return ThreadSearchCond.builder()
        .page(page)
        .sort(sort)
        .word(word)
        .categoryId(categoryId)
        .pageable(pageable)
        .build();
  }

}
