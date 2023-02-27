package gladiator.philosopher.admin.dto;

import gladiator.philosopher.thread.enums.ThreadStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class ModifyThreadStatusRequestDtoByAdmin {

  private final ThreadStatus threadStatus;
}
