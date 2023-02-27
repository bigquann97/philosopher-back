package gladiator.philosopher.admin.dto;

import gladiator.philosopher.comment.enums.CommentStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyCommentStatusRequestDtoByAdmin {

  private final CommentStatus commentStatus;
}
