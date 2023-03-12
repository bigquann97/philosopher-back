package gladiator.philosopher.thread.dto;

import lombok.Getter;

@Getter
public class ThreadSearchCondByAdmin {

  private String threadStatus;

  public ThreadSearchCondByAdmin(String threadStatus) {
    this.threadStatus = threadStatus;
  }
}
