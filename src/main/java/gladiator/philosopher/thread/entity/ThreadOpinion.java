package gladiator.philosopher.thread.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "thread_opinion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThreadOpinion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "thread_id")
  private Thread thread;

  private String opinion;

  @Builder
  public ThreadOpinion(Thread thread, String opinion) {
    this.thread = thread;
    this.opinion = opinion;
  }
}