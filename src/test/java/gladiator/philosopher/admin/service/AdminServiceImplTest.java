package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enumtype.GenderType;
import gladiator.philosopher.common.enumtype.UserStatus;
import gladiator.philosopher.common.enumtype.UserType;
import gladiator.philosopher.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class AdminServiceImplTest {

  @Autowired
  AccountRepository accountRepository;

  @BeforeEach
  public void addMembers(){
    Account account1 = new Account("test1@naver.com", "test1",10,"김초코",GenderType.MALE,UserType.ROLE_USER,UserStatus.ACTIVATED);
    Account account2 = new Account("test2@naver.com", "test2",12,"김망고",GenderType.MALE,UserType.ROLE_USER,UserStatus.ACTIVATED);
    Account account3 = new Account("test3@naver.com", "test3",14,"김우유",GenderType.MALE,UserType.ROLE_USER,UserStatus.ACTIVATED);

    accountRepository.save(account1);
    accountRepository.save(account2);
    accountRepository.save(account3);
  }

  @Test
  public void postDeleteTestByAdmin(){
    Account account4 = new Account("test4@naver.com", "test3",16,"김커피",GenderType.MALE,UserType.ROLE_USER,UserStatus.ACTIVATED);
    accountRepository.save(account4);
    Post post = new Post(account4, "title","content");
  }
}