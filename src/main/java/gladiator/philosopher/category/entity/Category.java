package gladiator.philosopher.category.entity;

import gladiator.philosopher.common.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Builder
  public Category(final String name) {
    this.name = name;
  }

  public Category modifyName(final String name) {
    this.name = name;
    return this;
  }

  //test 생성자
  public Category(Long id, String name){
    this.id =id;
    this.name = name;
  }
}
