package gladiator.philosopher.thread.entity;

import lombok.Getter;

@Getter
public enum Sort {
  NEW, // 최신 순 정렬
  RCMND // 좋아요 수 정렬
  ;
}
