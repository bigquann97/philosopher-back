package gladiator.philosopher.account.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class AccountInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageUrl;

  @OneToOne
  @JoinColumn(name = "accout_id")
  private Account account;

  public AccountInfo( Account account, String imageUrl) {
    this.imageUrl = imageUrl;
    this.account = account;
  }

}
