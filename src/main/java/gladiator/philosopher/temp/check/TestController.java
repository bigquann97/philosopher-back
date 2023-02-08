package gladiator.philosopher.temp.check;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {

  @GetMapping("/test1")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String test1() {
    return "hello";
  }

  @GetMapping("/test2")
  public String test2() {
    return "good";
  }
  @PreAuthorize("hasRole('ROLE_USER')")
  @GetMapping("/test3")
  public String test3() {
    return "아메바 컬쳐";
  }
}

