package gladiator.philosopher.thread.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThreadOpinion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String opinion;

  @ManyToOne
  @JoinColumn(name = "thread_id")
  private Thread thread;

  @Builder
  public ThreadOpinion(Thread thread, String opinion) {
    this.thread = thread;
    this.opinion = opinion;
  }

}