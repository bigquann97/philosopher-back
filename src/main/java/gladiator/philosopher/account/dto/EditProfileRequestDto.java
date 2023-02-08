package gladiator.philosopher.account.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class EditProfileRequestDto {

  private final String nickname;

  private final MultipartFile accountImage;

}
