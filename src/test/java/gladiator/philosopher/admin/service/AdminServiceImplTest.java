package gladiator.philosopher.admin.service;

import static org.assertj.core.api.Assertions.assertThat;

import gladiator.philosopher.Account.repository.AccountRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.thread.repository.ThreadRepository;
import gladiator.philosopher.thread.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminServiceImplTest {

  @Autowired
  AccountRepository accountRepository;
  @Autowired
  PostRepository postRepository;
  @Autowired
  ThreadService threadService;
  @Autowired
  ThreadRepository threadRepository;

/*  @BeforeEach
  public void addMembers() {
    Account account1 = new Account("test1@naver.com", "test1", 10, "김초코", Gender.MALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    Account account2 = new Account("test2@naver.com", "test2", 12, "김망고", Gender.MALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    Account account3 = new Account("test3@naver.com", "test3", 14, "김우유", Gender.MALE,
        UserRole.ROLE_USER, UserStatus.ACTIVATED);
    accountRepository.save(account1);
    accountRepository.save(account2);
    accountRepository.save(account3);
  }*/

//  @Test
//  public void threadSave() {
//    Account account4 = new Account(1L,"test4@naver.com", "test3", 16, "김커피", Gender.MALE,
//        UserRole.ROLE_USER, UserStatus.ACTIVATED);
//    accountRepository.save(account4);
//    Post post = new Post(account4, "title", "content");
//    postRepository.save(post);
//    Thread thread1 = threadService.startThread(post);
//    threadRepository.save(thread1);
//
//    assertThat(thread1.getTitle().equals(post.getTitle()));
//  }
}