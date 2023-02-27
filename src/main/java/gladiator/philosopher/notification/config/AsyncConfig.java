package gladiator.philosopher.notification.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(10);
    taskExecutor.setMaxPoolSize(50);
    taskExecutor.setQueueCapacity(100);
    taskExecutor.setThreadNamePrefix("async-thread-");
    taskExecutor.initialize();
    return taskExecutor;
  }
  
}