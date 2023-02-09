package gladiator.philosopher.admin.service;

import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final AccountRepository accountRepository;

  @Override
  public List<UserInfoResponseDto> getUsersInfoList() {
    return accountRepository.getInfoByAccount();
  }

}
