package gladiator.philosopher.account;

class AccountServiceTest {

}

/*

@SpringBootTest
@Transactional
@Slf4j

  @Autowired
  AccountService accountService;

  @Autowired
  AccountRepository accountRepository;

  @Test
  @DisplayName("1. 사용자 정보 가지고 오기 ")
  public void getAccountInfoCheck() {

    //given
    Account account = AccountFactory.createUserAccount1();

    Account mango = Account.builder()
        .email("qwer@naver.com")
        .password("rlawlghks1")
        .gender(Gender.MALE)
        .status(UserStatus.ACTIVATED)
        .role(UserRole.ROLE_USER)
        .age(15)
        .nickname("김망고")
        .build();
    accountRepository.save(mango);
    accountRepository.save(account);

//    when
    Account getAccount = accountService.getAccount(mango.getId());

//    then
    log.info(mango.getEmail());
    assertThat(getAccount.getEmail().equals(mango.getEmail()));
    assertThat(account.getEmail().equals("kiang18@naver.com"));

  }
 */