package gladiator.philosopher.common.util;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.repository.ThreadRepository;
import gladiator.philosopher.thread.service.ThreadService;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData implements ApplicationRunner {

  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;
  private final PostImageRepository postImageRepository;
  private final PostRepository postRepository;
  private final ThreadService threadService;
  private final ThreadRepository threadRepository;

  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {
    Account account1 = new Account("test1@naver.com", passwordEncoder.encode("test1"), 16, "김커피",
        Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    Account account2 = new Account("test2@naver.com", passwordEncoder.encode("test2"), 20, "김땅콩",
        Gender.FEMALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    Account account3 = new Account("test3@naver.com", passwordEncoder.encode("test3"), 25, "김초코",
        Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    Account account4 = new Account("test4@naver.com", passwordEncoder.encode("test4"), 30, "김우유",
        Gender.FEMALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    Account account5 = new Account("test5@naver.com", passwordEncoder.encode("test5"), 50, "김물물",
        Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);

    accountRepository.save(account1);
    accountRepository.save(account2);
    accountRepository.save(account3);
    accountRepository.save(account4);
    accountRepository.save(account5);

    List<PostImage> postImages = List.of(
        new PostImage("sample.jpg", null),
        new PostImage("sample.jpg", null),
        new PostImage("sample.jpg", null)
    );

    postImageRepository.saveAll(postImages);

    Thread thread = new Thread("title", "content", postImages, account1, LocalDateTime.now());

    threadRepository.save(thread);


  }

}
