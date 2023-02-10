package gladiator.philosopher.report.entity;

import lombok.Getter;

public enum ReportCategory {
  ABUSE("ABUSE"), // 욕설
  IRRELEVANT("IRRELEVANT"), // 게시글 취지와 상관없는 게시글
  ADVERTISEMENT("ADVERTISEMENT"), // 광고
  SEXUAL_HARASSMENT("SEXUAL_HARASSMENT"), // 성희롱
  SPAMMER("SPAMMER") // 도배
  ;

  @Getter
  private final String value;

  ReportCategory(String value) {
    this.value = value;
  }
}
