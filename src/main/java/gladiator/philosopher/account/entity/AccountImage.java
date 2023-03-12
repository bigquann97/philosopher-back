package gladiator.philosopher.account.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageUrl;

  @OneToOne
  @JoinColumn(name = "accout_id")
  private Account account;

  public AccountImage(Account account, String imageUrl) {
    this.imageUrl = imageUrl;
    this.account = account;
  }

  public void updateImageUrl(String url) {
    this.imageUrl = url;
  }

}
