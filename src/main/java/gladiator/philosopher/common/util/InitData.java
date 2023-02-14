package gladiator.philosopher.common.util;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountInfo;
import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostOpinion;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostOpinionRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.repository.ThreadRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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
  private final AccountInfoRepository accountInfoRepository;
  private final PostOpinionRepository postOpinionRepository;


  @Override
  @Transactional
  public void run(ApplicationArguments args) throws Exception {

    // 회원부 ( 5명 )
    Account account1 = new Account(1L, "kiang18@naver.com", passwordEncoder.encode("rlawlghks1"),
        20, "김지환", Gender.FEMALE, UserRole.ROLE_MASTER, UserStatus.ACTIVATED);
    accountRepository.save(account1);
    AccountInfo accountImage1 = new AccountInfo(account1, "jipang1.jpg");
    accountInfoRepository.save(accountImage1);

    Account account2 = new Account(2L, "test1@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
        "박정수", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account2);
    AccountInfo accountImage2 = new AccountInfo(account2, "jipang2.png");
    accountInfoRepository.save(accountImage2);

    Account account3 = new Account(3L, "test2@naver.com", passwordEncoder.encode("rlawlghks1"), 40,
        "이정국", Gender.MALE, UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account3);
    AccountInfo accountImage3 = new AccountInfo(account3, "jipang3.jpg");
    accountInfoRepository.save(accountImage3);

    Account account4 = new Account(4L, "test3@naver.com", passwordEncoder.encode("rlawlghks1"), 80,
        "하규호", Gender.MALE, UserRole.ROLE_ADMIN, UserStatus.ACTIVATED);
    accountRepository.save(account4);

    Account account5 = new Account(5L, "test4@naver.com", passwordEncoder.encode("rlawlghks1"), 100,
        "김관호", Gender.FEMALE, UserRole.ROLE_USER, UserStatus.SUSPENDED);
    accountRepository.save(account5);

    // 게시글 부
    List<String> opinions = Arrays.asList("opinion1", "opinion2", "opinion3");

    Post post1 = new Post(account1, "김지환의 테스트 데이터입니다.", "김지환의 테스트 데이터입니다", null);
    postRepository.save(post1);

    Post post2 = new Post(account2, "박정수의 테스트 데이터입니다.", "박정수의  데이터입니다", null);
    postRepository.save(post2);

    Post post3 = new Post(account3, "박정수의 테스트 데이터입니다.", "박정수의테스트 데이터입니다", null);
    postRepository.save(post3);

    Post post4 = new Post(account4, "하규호의 테스트 데이터입니다.", "하규호의테스트 데이터입니다", null);
    postRepository.save(post4);

    Post post5 = new Post(account4, "테스트 데이터입니다.", "테스트 데이터입니다", null);
    postRepository.save(post5);

    Post post6 = new Post(account5, "테스트 데이터입니다.", "테스트 데이터입니다", null);
    postRepository.save(post6);

    List<PostOpinion> list = opinions.stream().map(x -> new PostOpinion(post1, x))
        .collect(Collectors.toList());
    List<PostOpinion> list2 = opinions.stream().map(x -> new PostOpinion(post2, x))
        .collect(Collectors.toList());
    List<PostOpinion> list3 = opinions.stream().map(x -> new PostOpinion(post3, x))
        .collect(Collectors.toList());
    List<PostOpinion> list4 = opinions.stream().map(x -> new PostOpinion(post4, x))
        .collect(Collectors.toList());
    List<PostOpinion> list5 = opinions.stream().map(x -> new PostOpinion(post5, x))
        .collect(Collectors.toList());
    List<PostOpinion> list6 = opinions.stream().map(x -> new PostOpinion(post6, x))
        .collect(Collectors.toList());

    postOpinionRepository.saveAll(list);
    postOpinionRepository.saveAll(list2);
    postOpinionRepository.saveAll(list3);
    postOpinionRepository.saveAll(list4);
    postOpinionRepository.saveAll(list5);
    postOpinionRepository.saveAll(list6);

    // 쓰레드 부
    Thread thread2 = new Thread(post4.getTitle(), post4.getContent(), account4, LocalDateTime.now(),
        null);
    threadRepository.save(thread2);
    Thread thread3 = new Thread(post5.getTitle(), post5.getContent(), account5, LocalDateTime.now(),
        null);
    threadRepository.save(thread3);


  }

}
