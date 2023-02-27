package gladiator.philosopher.admin.dto;

import gladiator.philosopher.post.enums.PostStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyPostStatusRequestDtoByAdmin {

  private final PostStatus postStatus;

  public ModifyPostStatusRequestDtoByAdmin(PostStatus postStatus) {
    this.postStatus = postStatus;
  }
}
