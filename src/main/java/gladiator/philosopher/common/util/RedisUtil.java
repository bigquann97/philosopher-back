package gladiator.philosopher.common.util;

import java.time.Duration;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisUtil {

  private final RedisTemplate<String, Object> redisTemplate;
  private final StringRedisTemplate stringRedisTemplate;

  public String getData(String key) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
    return valueOperations.get(key);
  }

  public void setData(String key, String value) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
    valueOperations.set(key, value);
  }

  public void setDataExpire(String key, String value, long duration) {
    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
    Duration expireDuration = Duration.ofSeconds(duration);
    valueOperations.set(key, value, expireDuration);
  }

  public boolean hasKey(String key) {
    return stringRedisTemplate.hasKey(key);
  }

  public void deleteData(String key) {
    stringRedisTemplate.delete(key);
  }

  public void addHashData(String key, String id, String endTime) {
    HashOperations<String, String, String> valueOperations = redisTemplate.opsForHash();
    valueOperations.put(key, id, endTime);
  }

  public Map<String, String> getAllHashData(String key) {
    HashOperations<String, String, String> valueOperations = redisTemplate.opsForHash();
    return valueOperations.entries(key);
  }

  public void deleteHashData(String key, String id) {
    HashOperations<String, String, String> valueOperations = redisTemplate.opsForHash();
    valueOperations.delete(key, id);
  }
  
}