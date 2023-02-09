package gladiator.philosopher.thread.service;

import gladiator.philosopher.thread.repository.ThreadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

  private final ThreadRepository threadRepository;

}
