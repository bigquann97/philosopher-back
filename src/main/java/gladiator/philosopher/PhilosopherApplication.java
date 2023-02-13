package gladiator.philosopher;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhilosopherApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhilosopherApplication.class, args);
  }
//  @Bean
//  JPAQueryFactory jpaQueryFactory(EntityManager em) {
//    return new JPAQueryFactory(em);
//  }

}