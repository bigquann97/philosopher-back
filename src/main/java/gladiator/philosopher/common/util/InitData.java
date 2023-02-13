package gladiator.philosopher.common.util;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.repository.ThreadRepository;
import gladiator.philosopher.thread.service.ThreadService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class InitData implements ApplicationRunner {

  private final PasswordEncoder passwordEncoder;
  private final AccountRepository accountRepository;

  private final ThreadRepository threadRepository;
  private final PostRepository postRepository;

  private final PostImageRepository postImageRepository;
  private final ThreadService threadService;


  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {

    // 회원부  ( 5명 )
    Account account1 = new Account(1L, "kiang18@naver.com", passwordEncoder.encode("rlawlghks1"),
        20, "김지환", Gender.FEMALE, UserRole.ROLE_MASTER, UserStatus.ACTIVATED);
    accountRepository.save(account1);

    Account account2 = new Account(2L, "test1@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
        "박정수", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account2);

    Account account3 = new Account(3L, "test2@naver.com", passwordEncoder.encode("rlawlghks1"), 60,
        "김기리", Gender.FEMALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account3);

    Account account4 = new Account(4L, "test3@naver.com", passwordEncoder.encode("rlawlghks1"), 80,
        "하규호", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account4);

    Account account5 = new Account(5L, "test4@naver.com", passwordEncoder.encode("rlawlghks1"), 100,
        "김관호", Gender.FEMALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account5);

    // 게시글 부
    Post post1 = new Post(account1, "김지환의 테스트 데이터입니다.", "김지환의 테스트 데이터입니다");
    postRepository.save(post1);

    Post post2 = new Post(account2, "박정수의 테스트 데이터입니다.", "박정수의  데이터입니다");
    postRepository.save(post2);

    Post post3 = new Post(account3, "박정수의 테스트 데이터입니다.", "박정수의테스트 데이터입니다");
    postRepository.save(post3);

    Post post4 = new Post(account4, "하규호의 테스트 데이터입니다.", "하규호의테스트 데이터입니다");
    postRepository.save(post4);

    Post post5 = new Post(account4, "테스트 데이터입니다.", "테스트 데이터입니다");
    postRepository.save(post5);

    Post post6 = new Post(account5, "테스트 데이터입니다.", "테스트 데이터입니다");
    postRepository.save(post6);

    List<PostImage> images = new ArrayList<>();
    // 쓰레드 부

    Thread thread2 = new Thread(post4.getTitle(), post4.getContent(), null, account4,
        LocalDateTime.now());
    threadRepository.save(thread2);
    Thread thread3 = new Thread(post5.getTitle(), post5.getContent(), null, account5,
        LocalDateTime.now());
    threadRepository.save(thread3);

    // 김관호 작업
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
