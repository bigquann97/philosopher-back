package gladiator.philosopher.mention.controller;

import gladiator.philosopher.mention.service.MentionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MentionController {

  private final MentionService mentionService;

}
