package gladiator.philosopher.auth.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.INVALID_CODE;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.INVALID_EMAIL_OR_PW;

import gladiator.philosopher.common.exception.AuthException;
import gladiator.philosopher.common.util.EmailMessage;
import gladiator.philosopher.common.util.RedisUtil;
import java.util.UUID;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;
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
    MimeMessage message = emailSender.createMimeMessage();

    try {
      message.addRecipient(RecipientType.TO, new InternetAddress(to));
      message.setSubject(sub, "utf-8");
      message.setText(text, "utf-8", "html");
    } catch (MessagingException e) {
      throw new IllegalArgumentException("이메일 전송 실패");
    }
    emailSender.send(message);
  }

  @Transactional
  public void sendVerificationMail(final String email) {
    if (email == null) {
      throw new AuthException(INVALID_EMAIL_OR_PW);
    }
    String code = UUID.randomUUID().toString().substring(0, 7).toUpperCase();
    redisUtil.setDataExpire(VERIFY_KEY_PREFIX + email, code, 60 * 3L); // 3분 인증 시간
    sendMail(email, EmailMessage.MESSAGE_TITLE, EmailMessage.createMessage(code));
  }

  @Transactional
  public void verifyEmail(final String email, final String code) {
    String validCode = redisUtil.getData(VERIFY_KEY_PREFIX + email);
    if (!code.equals(validCode)) {
      throw new AuthException(INVALID_CODE);
    }
    redisUtil.setDataExpire(WHITELIST_KEY_PREFIX + email, "true",
        30L); // 1일 이메일 화이트리스트 유지
    redisUtil.deleteData(VERIFY_KEY_PREFIX + email);
  }

}
