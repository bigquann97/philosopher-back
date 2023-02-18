package gladiator.philosopher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
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