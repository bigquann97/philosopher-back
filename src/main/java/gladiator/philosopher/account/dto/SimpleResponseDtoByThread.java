package gladiator.philosopher.account.dto;

import gladiator.philosopher.thread.enums.ThreadLocation;
import lombok.Getter;

@Getter
public class SimpleResponseDtoByThread {

  private Long id;
  private String title;
  private String content;
  private ThreadLocation threadLocation;

  public SimpleResponseDtoByThread(Long id, String title, String content,
      ThreadLocation threadLocation) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.threadLocation = threadLocation;
  }
}
