package gladiator.philosopher.mention;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class MentionServiceTest {

  @Test
  void test() {
    String str = " #36 #10 #55 안녕하세요 #하하2 #1 ";
    String k = str.trim().replaceAll(" ", "");

    Long num = parseFirstHashtagNum(k);
    ArrayList<Long> arr = new ArrayList<>();
    while (num != null) {
      arr.add(num);
      k = k.replaceAll("#" + num, "");
      num = parseFirstHashtagNum(k);
    }

    System.out.println(arr);
  }

  private Long parseFirstHashtagNum(String str) {
    int firstSharpIdx = str.indexOf("#");
    int pointer = firstSharpIdx + 1;

    while (str.charAt(pointer) >= 48 && str.charAt(pointer) <= 57) {
      pointer++;
    }

    String num = str.substring(firstSharpIdx + 1, pointer);

    try {
      return Long.parseLong(num);
    } catch (NumberFormatException e) {
      return null;
    }
  }

}