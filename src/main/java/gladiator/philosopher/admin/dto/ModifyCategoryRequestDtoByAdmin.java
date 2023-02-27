package gladiator.philosopher.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class ModifyCategoryRequestDtoByAdmin {

  private final Long categoryId;
}
