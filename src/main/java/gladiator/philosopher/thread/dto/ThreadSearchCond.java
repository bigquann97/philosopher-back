package gladiator.philosopher.thread.dto;

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
  private final Sort sort; // 정렬기준 - 없음, 날짜, 조회수, 좋아요수
  private final String word; // 검색어
  private final Pageable pageable;

  @Builder
  public ThreadSearchCond(int page, Sort sort, String word, Pageable pageable) {
    this.page = page;
    this.sort = sort;
    this.word = word;
    this.pageable = pageable;
  }

  public static ThreadSearchCond of(
      Integer page,
      Sort sort,
      String word
  ) {
    Pageable pageable;

    page = (page == null) || (page <= 0) ? 0 : page - 1;
    sort = sort == null ? Sort.NEW : sort;
    word = (word == null) ? "" : word;
    pageable = PageRequest.of(page, 10, Direction.DESC, sort.getOption());

    System.out.println(sort.getOption());

    return ThreadSearchCond.builder()
        .page(page)
        .sort(sort)
        .word(word)
        .pageable(pageable)
        .build();
  }

}
