package gladiator.philosopher.mention;

import java.util.LinkedHashSet;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MentionServiceTest {

  @DisplayName("3. 멘션 # 숫자 인식 테스트")
  @Test
  void test_3() {
    String str = "#33 #12 #67 구구구 #1 # #하#88하하 #6 # 10 #tmj # 1 9 # 30";
    String str2 = "# 하하호호 # 09";
    String str3 = "1#3";
    String str4 = "! #@55 ##2# 531 ";
    String str5 = "505992# 1123 ###!67 ㅡ';#ㅣㅣ2#1";
    String str6 = "3";
    String str7 = "";

    Set<Long> longs = parseAllHashtagNums(str);
    Set<Long> longs2 = parseAllHashtagNums(str2);
    Set<Long> longs3 = parseAllHashtagNums(str3);
    Set<Long> longs4 = parseAllHashtagNums(str4);
    Set<Long> longs5 = parseAllHashtagNums(str5);
    Set<Long> longs6 = parseAllHashtagNums(str6);
    Set<Long> longs7 = parseAllHashtagNums(str7);

    Assertions.assertThat(longs).containsAll(Set.of(33L, 12L, 67L, 1L, 88L, 6L));
    Assertions.assertThat(longs2).isEmpty();
    Assertions.assertThat(longs3).containsAll(Set.of(3L));
    Assertions.assertThat(longs4).containsAll(Set.of(2L));
    Assertions.assertThat(longs5).containsAll(Set.of(1L));
    Assertions.assertThat(longs6).isEmpty();
    Assertions.assertThat(longs7).isEmpty();
  }

  private Set<Long> parseAllHashtagNums(String body) {
    Set<Long> hashtagNums = new LinkedHashSet<>();

    while (body.contains("#")) {
      Long num;
      int sharpIdx = body.indexOf("#");
      int pointer = sharpIdx + 1;

      while (pointer < body.length() && body.charAt(pointer) >= 48 && body.charAt(pointer) <= 57) {
        pointer++;
      }

      String numStr = body.substring(sharpIdx + 1, pointer);

      try {
        num = Long.parseLong(numStr);
      } catch (NumberFormatException e) {
        num = null;
      }

      if (num != null) {
        hashtagNums.add(num);
      }

      body = body.substring(pointer);
    }

    return hashtagNums;
  }

}