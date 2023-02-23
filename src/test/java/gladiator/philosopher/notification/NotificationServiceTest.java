package gladiator.philosopher.notification;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NotificationServiceTest {

  @DisplayName("1. ")
  @Test
  void test_1() {

    String content = "\"" + "깻잎 논쟁" + "\""
        + " 토론장에서 " + "가나다" + " 님의 "
        + 36 + "번 댓글이 "
        + "당신의 " + 12 + "번 댓글을 언급했습니다!";

    System.out.println(content);
  }

}
