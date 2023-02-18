package gladiator.philosopher.common.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.PageImpl;

@Getter
public class MyPage<T> {

  private List<T> content;

  private Long totalElements;

  private int totalPage;

  private int currPage;

  private boolean isLast;

  private boolean empty;

  public MyPage(PageImpl<T> page) {
    this.content = page.getContent();
    this.totalPage = page.getTotalPages();
    this.isLast = page.isLast();
    this.totalElements = page.getTotalElements();
    this.currPage = page.getNumber() + 1;
    this.empty = page.isEmpty();
  }

}
