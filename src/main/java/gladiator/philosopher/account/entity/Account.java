package gladiator.philosopher.account.entity;

import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
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
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole type;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @Builder
  public Account(String email, String password, int age, String nickname, Gender gender,
      UserRole type, UserStatus status) {
    this.email = email;
    this.password = password;
    this.age = age;
    this.nickname = nickname;
    this.gender = gender;
    this.type = type;
    this.status = status;
  }


  // testcode
  public Account(Long id,String email, String password, int age, String nickname,
      Gender gender, UserRole type, UserStatus status) {
    this.id=id;
    this.email = email;
    this.password = password;
    this.age = age;
    this.nickname = nickname;
    this.gender = gender;
    this.type = type;
    this.status = status;
  }

  public void UpdateAccountRole(UserRole role){
    this.type = role;
  }

  public void UpdateNickname(String nickname){
    this.nickname = nickname;
  }

  public void UpdatePassword(String password){
    this.password = password;
  }

}
