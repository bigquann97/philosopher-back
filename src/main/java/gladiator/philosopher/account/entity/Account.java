package gladiator.philosopher.account.entity;

import gladiator.philosopher.common.entity.BaseEntity;
import gladiator.philosopher.common.enums.Gender;
import gladiator.philosopher.common.enums.UserRole;
import gladiator.philosopher.common.enums.UserStatus;
import java.util.Objects;
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
import org.hibernate.Hibernate;

@Entity
@Getter
@NoArgsConstructor
public class Account extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private int age;

  @Column(nullable = false, unique = true)
  private String nickname;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole role;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserStatus status;

  @Builder
  public Account(String email, String password, int age, String nickname, Gender gender,
      UserRole role, UserStatus status) {
    this.email = email;
    this.password = password;
    this.age = age;
    this.nickname = nickname;
    this.gender = gender;
    this.role = role;
    this.status = status;
  }

  // testcode
  public Account(Long id, String email, String password, int age, String nickname,
      Gender gender, UserRole role, UserStatus status) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.age = age;
    this.nickname = nickname;
    this.gender = gender;
    this.role = role;
    this.status = status;
  }

  public void updateAccountRole(UserRole role){
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Account account = (Account) o;
    return id != null && Objects.equals(id, account.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public void updateNickname(String nickname){
    this.nickname = nickname;
  }

  public void updatePassword(String password){
    this.password = password;
  }

}
