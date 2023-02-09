package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.EditProfileRequestDto;
import gladiator.philosopher.account.entity.Account;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

  void editProfile(MultipartFile multipartFile, final EditProfileRequestDto req,
      final Account member);
}
