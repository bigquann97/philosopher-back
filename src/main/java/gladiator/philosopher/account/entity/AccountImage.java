package gladiator.philosopher.account.entity;

import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.enums.ExceptionStatus;
import java.util.Arrays;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
public class AccountImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String imageUrl;

  @OneToOne
  @JoinColumn(name = "accout_id")
  private Account account;


}
