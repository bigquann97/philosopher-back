package gladiator.philosopher.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExeTimer {
  // Quann - 시간 측정을 하고싶은 메서드 위에 @ExeTimer Annotation 달아주시면 됩니다.
  // 해당 메서드에 대하여 Log를 찍다보니, 성능 테스트로 사용 후 해당 메서드 지워주시면 감사하겠습니다.
}
