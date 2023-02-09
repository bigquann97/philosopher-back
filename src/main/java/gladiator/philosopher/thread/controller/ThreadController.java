package gladiator.philosopher.thread.controller;


import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ThreadController {

  private final ThreadService threadService;

}
