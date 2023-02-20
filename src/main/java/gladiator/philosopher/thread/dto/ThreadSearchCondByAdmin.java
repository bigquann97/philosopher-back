package gladiator.philosopher.thread.dto;

import gladiator.philosopher.thread.entity.ThreadStatus;
import lombok.Getter;

@Getter
public class ThreadSearchCondByAdmin {

  private ThreadStatus threadStatus;

  public ThreadSearchCondByAdmin(ThreadStatus threadStatus) {
    this.threadStatus = threadStatus;
  }
}
