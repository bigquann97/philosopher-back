package gladiator.philosopher.common.util;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
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
  
  public void deleteData(String key) {
    stringRedisTemplate.delete(key);
  }

  public boolean hasKey(String key) {
    return stringRedisTemplate.hasKey(key);
  }
}