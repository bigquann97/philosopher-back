package gladiator.philosopher.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
@NoArgsConstructor
public class EmitterRepositoryImpl implements EmitterRepository {

  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
  private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

  @Override
  public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
    emitters.put(emitterId, sseEmitter);
    return sseEmitter;
  }

  @Override
  public void saveEventCache(String eventCacheId, Object event) {
    eventCache.put(eventCacheId, event);
  }

  @Override
  public void deleteEventCache(String eventCacheId, Object event) {
    eventCache.remove(eventCacheId, event);
  }

  @Override
  public Map<String, SseEmitter> findAllEmitterStartWithByAccountId(String accountId) {
    return emitters.entrySet().stream()
        .filter(entry -> entry.getKey().split("_")[0].equals(accountId))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public Map<String, Object> findAllEventCacheStartWithByMemberId(String accountId) {
    return eventCache.entrySet().stream()
        .filter(entry -> entry.getKey().split("_")[0].equals(accountId))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public void deleteById(String id) {
    emitters.remove(id);
  }

  @Override
  public void deleteAllEmitterStartWithId(String accountId) {
    emitters.forEach(
        (key, emitter) -> {
          if (key.startsWith(accountId)) {
            emitters.remove(key);
          }
        }
    );
  }

  @Override
  public void deleteAllEventCacheStartWithId(String accountId) {
    eventCache.forEach(
        (key, emitter) -> {
          if (key.startsWith(accountId)) {
            eventCache.remove(key);
          }
        }
    );
  }

}