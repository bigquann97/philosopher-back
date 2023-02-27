package gladiator.philosopher.thread.service;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ThreadServiceImplTest {

  @DisplayName("1. ")
  @Test
  void test_1() {
    int hour = LocalDateTime.now().getHour();
    int minute = LocalDateTime.now().getMinute();
    int second = LocalDateTime.now().getSecond();
    int nano = LocalDateTime.now().getNano();

    LocalDateTime localDateTime = LocalDateTime.now()
        .plusDays(3)
        .minusHours(hour)
        .minusMinutes(minute)
        .minusSeconds(second);

    LocalDateTime endTime = LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
        localDateTime.getDayOfMonth(),
        localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond());

    System.out.println(endTime);

    System.out.println(localDateTime);
  }

  @DisplayName("2. ")
  @Test
  void test_2() {
    // 2일 후 자정
    LocalDateTime time = LocalDateTime.now()
        .plusDays(3)
        .minusHours(LocalDateTime.now().getHour())
        .minusMinutes(LocalDateTime.now().getMinute());

    LocalDateTime endDate = LocalDateTime.of(time.getYear(), time.getMonthValue(),
        time.getDayOfMonth(),
        time.getHour(),
        time.getMinute());

    System.out.println(endDate);
  }

}