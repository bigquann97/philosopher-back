package gladiator.philosopher.temp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

  public static String getCurrentMemberEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      throw new RuntimeException("security Context에 인증정보가 없습니다");
    } else if (authentication.getAuthorities() == null) {
      throw new RuntimeException("security Context에 인증객체 정보가 없습니다2");
    }
    return authentication.getName();
  }

}

// 솔직히 이거 안 쓸 것 같은데 그냥 날려버려도 될 듯? ->
