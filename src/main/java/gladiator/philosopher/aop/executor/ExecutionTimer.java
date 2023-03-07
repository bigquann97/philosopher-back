package gladiator.philosopher.aop.executor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class ExecutionTimer {

  @Pointcut("@annotation(gladiator.philosopher.aop.annotation.ExeTimer)")
  private void timer() {
  }

  // Quann - 시간 측정을 하고싶은 메서드 위에 @ExeTimer Annotation 달아주시면 됩니다.
  // 해당 메서드에 대하여 Log를 찍다보니, 성능 테스트로 사용 후 해당 메서드 지워주시면 감사하겠습니다.
  @Around("timer()")
  public Object AssumeExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    Object proceed = null;
    String className = joinPoint.getSignature().getName();

    try {
      stopWatch.start();
      proceed = joinPoint.proceed();
    } finally {
      stopWatch.stop();
      log.info("{} elapsed time :: {}", className, stopWatch.getTotalTimeMillis());
    }

    return proceed;
  }
}
