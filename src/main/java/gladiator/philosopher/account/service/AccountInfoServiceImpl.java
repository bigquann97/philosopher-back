package gladiator.philosopher.account.service;

import gladiator.philosopher.account.repository.AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService{

  private final AccountInfoRepository accountInfoRepository;

  @Override
  public String selectAccountImageUrl(Long id) {
    return accountInfoRepository.getAccountImageById(id);
  }
}
