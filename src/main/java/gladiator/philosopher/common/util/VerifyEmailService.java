package gladiator.philosopher.common.util;

import gladiator.philosopher.account.repository.AccountRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VerifyEmailService {

  private final AccountRepository accountRepository;
  private final RedisUtil redisUtil;
  private final JavaMailSender emailSender;
  private static final String VERIFY_KEY_PREFIX = "EMAIL:VERIFY:"; // EMAIL:VERIFY:email - A3GA1E
  private static final String WHITELIST_KEY_PREFIX = "EMAIL:VERIFIED"; // EMAIL:VERIFIED - email, email ...

  public void sendMail(String to, String sub, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(sub);
    message.setText(text);
    emailSender.send(message);
  }

  @Transactional
  public void sendVerificationMail(String email) {
    if (email == null) {
      throw new IllegalArgumentException("멤버가 조회되지 않음");
    }
    String code = UUID.randomUUID().toString().substring(0, 7).toUpperCase();
    redisUtil.setDataExpire(VERIFY_KEY_PREFIX + email, code, 60 * 3L); // 3분 인증 시간
    sendMail(email, EmailMessage.MESSAGE_TITLE, EmailMessage.createMessage(code));
  }

  @Transactional
  public void verifyEmail(String email, String code) {
    String validCode = redisUtil.getData(VERIFY_KEY_PREFIX + email);
    if (!code.equals(validCode)) {
      throw new IllegalArgumentException("코드 번호가 일치하지 않습니다.");
    }
    redisUtil.addSetData(WHITELIST_KEY_PREFIX, email);
    redisUtil.deleteData(VERIFY_KEY_PREFIX + email);
  }

}