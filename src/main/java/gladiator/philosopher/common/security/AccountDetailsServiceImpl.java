package gladiator.philosopher.common.security;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountDetailsServiceImpl implements UserDetailsService {

  private final AccountRepository accountRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException(ExceptionStatus.NOT_FOUND_ACCOUNT));
    return new AccountDetails(account);
  }

}