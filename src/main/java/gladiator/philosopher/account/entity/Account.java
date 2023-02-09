package gladiator.philosopher.account.entity;

import gladiator.philosopher.common.BaseEntity;
import gladiator.philosopher.common.enumtype.GenderType;
import gladiator.philosopher.common.enumtype.UserStatus;
import gladiator.philosopher.common.enumtype.UserType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private int age;

  @Column(nullable = false)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private GenderType gender;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserType type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @Builder
  public Account(String email, String password, int age, String nickname, GenderType gender,
      UserType type, UserStatus status, AccountImage accountImage) {
    this.email = email;
    this.password = password;
    this.age = age;
    this.nickname = nickname;
    this.gender = gender;
    this.type = type;
    this.status = status;
    this.accountImage = accountImage;
  }

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_image_id")
  private AccountImage accountImage;

  public void changeProfile(String nickname, AccountImage accountImage) {
    this.nickname = nickname;
    if (!accountImage.getOriginalName().equals("default_image.jpg")) {
      this.accountImage = accountImage;
    }
  }

  public boolean hasDefaultAccountImage() {
    return this.accountImage.getUniqueName().equals("default_image.jpg");
  }

}
