package gladiator.philosopher.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeAdapter {

  public static String formatToString(LocalDateTime localDateTime) {
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
  }

}
