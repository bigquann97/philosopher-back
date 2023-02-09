package gladiator.philosopher.admin.service;

import static org.junit.jupiter.api.Assertions.*;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.enumtype.GenderType;
import gladiator.philosopher.common.enumtype.UserStatus;
import gladiator.philosopher.common.enumtype.UserType;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
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

  }
}