package gladiator.philosopher.auth.service;

import gladiator.philosopher.common.util.EmailMessage;
import gladiator.philosopher.common.util.RedisUtil;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender emailSender;
  private final RedisUtil redisUtil;

  public static final String VERIFY_KEY_PREFIX = "EMAIL:VERIFY:"; // EMAIL:VERIFY:email - A3GA1E
  public static final String WHITELIST_KEY_PREFIX = "EMAIL:VERIFIED:"; // EMAIL:VERIFIED - email, email ...

  public void sendMail(
      final String to,
      final String sub,
      final String text
  ) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(sub);
    message.setText(text);
    emailSender.send(message);
  }

  @Transactional
  public void sendVerificationMail(final String email) {
    if (email == null) {
      throw new IllegalArgumentException("멤버가 조회되지 않음");
    }
    String code = UUID.randomUUID().toString().substring(0, 7).toUpperCase();
    redisUtil.setDataExpire(VERIFY_KEY_PREFIX + email, code, 60 * 3L); // 3분 인증 시간
    sendMail(email, EmailMessage.MESSAGE_TITLE, EmailMessage.createMessage(code));
  }

  @Transactional
  public void verifyEmail(final String email, final String code) {
    String validCode = redisUtil.getData(VERIFY_KEY_PREFIX + email);
    if (!code.equals(validCode)) {
      throw new IllegalArgumentException("코드 번호가 일치하지 않습니다.");
    }
    redisUtil.setDataExpire(WHITELIST_KEY_PREFIX + email, "true",
        30L); // 1일 이메일 화이트리스트 유지
    redisUtil.deleteData(VERIFY_KEY_PREFIX + email);
  }

}
