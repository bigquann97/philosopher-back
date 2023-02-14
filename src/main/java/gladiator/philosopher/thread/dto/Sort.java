package gladiator.philosopher.thread.dto;

import lombok.Getter;

@Getter
public enum Sort {
  NEW("createdDate"), // 최신 순 정렬
  RCMND("recommendation") // 좋아요 수 정렬
  ;

  private final String option;

  Sort(String option) {
    this.option = option;
  }

}
