package gladiator.philosopher.common.util;

import lombok.Getter;

@Getter
public class EmailMessage {

  public final static String MESSAGE_TITLE = "우리 모두 철학자 - 회원가입 인증 메일입니다.\n\n";

  public final static String MESSAGE_HEADER = "안녕하세요. '우리 모두 철학자'에 회원 가입을 신청해주셔서 감사합니다.\n\n";

  public final static String MESSAGE_FOOTER = "  를 코드 입력란에 입력해주세요.\n\n" + "감사합니다.";

  public static String createMessage(String code) {
    return MESSAGE_HEADER + code + MESSAGE_FOOTER;
  }

}
