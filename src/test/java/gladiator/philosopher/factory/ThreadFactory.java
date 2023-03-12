package gladiator.philosopher.factory;

import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadImage;
import gladiator.philosopher.thread.entity.ThreadOpinion;
import java.time.LocalDateTime;

public class ThreadFactory {

  public static Thread createThread1() {
    return Thread.builder()
        .category(CategoryFactory.createCategory1())
        .account(AccountFactory.createUserAccount1())
        .endDate(LocalDateTime.now().plusDays(1L))
        .content("쓰레드 내용")
        .title("쓰레드 제목")
        .build();
  }

  public static Thread createThread2() {
    return Thread.builder()
        .category(CategoryFactory.createCategory2())
        .account(AccountFactory.createUserAccount2())
        .endDate(LocalDateTime.now().plusDays(1L))
        .content("쓰레드 내용2")
        .title("쓰레드 제목2")
        .build();
  }

  public static ThreadOpinion createThreadOpinion1() {
    return ThreadOpinion.builder()
        .opinion("opinion1")
        .thread(createThread1())
        .build();
  }

  public static ThreadOpinion createThreadOpinion2() {
    return ThreadOpinion.builder()
        .opinion("opinion2")
        .thread(createThread1())
        .build();
  }

  public static ThreadOpinion createThreadOpinion3() {
    return ThreadOpinion.builder()
        .opinion("opinion2")
        .thread(createThread1())
        .build();
  }

  public static ThreadImage createThreadImage1() {
    return ThreadImage.builder()
        .thread(createThread1())
        .imageUrl("url1")
        .build();
  }

  public static ThreadImage createThreadImage2() {
    return ThreadImage.builder()
        .thread(createThread1())
        .imageUrl("url2")
        .build();
  }

  public static ThreadImage createThreadImage3() {
    return ThreadImage.builder()
        .thread(createThread1())
        .imageUrl("url3")
        .build();
  }

}
