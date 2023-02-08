package gladiator.philosopher.post.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@NoArgsConstructor
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "userId")
//  User user;

  @Column(nullable = false)
  String title;

  @Column(nullable = false)
  String content;

  @Column(nullable = false)
  String image;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createDate;

  @LastModifiedDate
  private LocalDateTime modDate;

  public Post(Long id, String title, String content, String image, LocalDateTime createDate,
      LocalDateTime modDate) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.image = image;
    this.createDate = createDate;
    this.modDate = modDate;
  }
}
