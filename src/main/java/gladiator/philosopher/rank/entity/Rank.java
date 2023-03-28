package gladiator.philosopher.rank.entity;

import gladiator.philosopher.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ranking")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rank extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String nickname;

  private Long count = 0L;

  private Philosopher philosopher;

  public Rank updateCount() {
    this.count++;
    return this;
  }

  @Builder
  public Rank(String nickname, Long count) {
    this.nickname = nickname;
    this.count = count;
  }

}
