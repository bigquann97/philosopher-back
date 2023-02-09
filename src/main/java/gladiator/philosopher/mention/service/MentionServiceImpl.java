package gladiator.philosopher.mention.service;

import gladiator.philosopher.mention.repository.MentionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentionServiceImpl implements MentionService {

  private final MentionRepository mentionRepository;

}
