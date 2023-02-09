package gladiator.philosopher.account.service;

import gladiator.philosopher.account.dto.EditProfileRequestDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.entity.AccountImage;
import gladiator.philosopher.account.repository.AccountImageRepository;
import gladiator.philosopher.account.repository.AccountRepository;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.common.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

  private final AccountRepository accountRepository;
  private final AccountImageRepository accountImageRepository;
  private final ImageService imageService;

  @Override
  @Transactional
  public void editProfile(
      final MultipartFile multipartFile,
      final EditProfileRequestDto req,
      final Account member) {
    Account account = accountRepository.findByEmail(member.getEmail()).orElseThrow(
        () -> new CustomException(ExceptionStatus.NOT_MATCH_INFORMATION)
    );

    AccountImage afterImage = new AccountImage(multipartFile.getOriginalFilename());

    validateNickname(req.getNickname());

    if (account.hasDefaultAccountImage()) { // 기본 프사 유저의 요청의 경우
      account.changeProfile(req.getNickname(), afterImage);
      accountImageRepository.save(afterImage);
    } else { // 이미 프사를 등록한 적이 있는 경우 - 이미 있는 프사 DB에서 삭제 필요
      AccountImage beforeImage = account.getAccountImage();

      account.changeProfile(req.getNickname(), afterImage);

      imageService.delete(beforeImage.getUniqueName());

      accountImageRepository.delete(beforeImage);
      accountImageRepository.save(afterImage);
    }
    imageService.upload(multipartFile, afterImage.getUniqueName());
  }

  private void validateNickname(final String nickName) {
    if (accountRepository.existsByNickname(nickName)) {
      throw new CustomException(ExceptionStatus.ACCOUNT_NICKNAME_IS_EXIST);
    }
  }

}
