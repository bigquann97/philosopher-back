package gladiator.philosopher.common.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@Getter
public class MyPage<T> {

  private List<T> content;

  private int numberOfElements;
  private long totalElements;
  private int totalPages;
  private int number;
  private int size;
  private boolean first;
  private boolean last;

  public MyPage(PageImpl<T> page) {
    this.content = page.getContent();
    this.numberOfElements = page.getNumberOfElements();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.number = page.getNumber();
    this.size = page.getSize();
    this.first = page.isFirst();
    this.last = page.isLast();
  }

  public MyPage(Page<T> page){
    this.content = page.getContent();
    this.numberOfElements = page.getNumberOfElements();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.number = page.getNumber();
    this.size = page.getSize();
    this.first = page.isFirst();
    this.last = page.isLast();
  }

}
