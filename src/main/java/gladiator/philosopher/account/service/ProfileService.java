package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.EditProfileRequestDto;
import gladiator.philosopher.account.entity.Account;

public interface ProfileService {

  void editProfile(final EditProfileRequestDto req, final Account member);
}
