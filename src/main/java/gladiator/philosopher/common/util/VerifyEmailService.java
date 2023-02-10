package gladiator.philosopher.common.util;

import gladiator.philosopher.account.repository.AccountRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VerifyEmailService {

  private final AccountRepository accountRepository;
  private final RedisUtil redisUtil;
  private final JavaMailSender emailSender;

  public void sendMail(String to, String sub, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(sub);
    message.setText(text);
    emailSender.send(message);
  }

  public void sendVerificationMail(String email) {
    String VERIFICATION_LINK = "http://localhost:8080/api/account/verify/";
    if (email == null) {
      throw new IllegalArgumentException("멤버가 조회되지 않음");
    }
    String code = UUID.randomUUID().toString().substring(0, 7);
    // redis에 링크 정보 저장
    redisUtil.setDataExpire(email, code, 60 * 30L);
    // 인증 링크 전송
    sendMail(email, EmailMessage.MESSAGE_TITLE, EmailMessage.createMessage(code));
  }

  public void verifyEmail(String email, String key) {
    String code = redisUtil.getData(email);
    if (!code.equals(key)) {
      throw new IllegalArgumentException("코드 번호가 일치하지 않습니다.");
    }
    redisUtil.deleteData(email);
  }
  
}