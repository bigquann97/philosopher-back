package gladiator.philosopher.post.entity;

import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import java.util.Arrays;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostImages {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String originalName;

  private String uniqueName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  private Post post;

  private final static String supportedExtension[] = {"jpg", "jpeg", "bmp", "png"};

  @Builder
  public PostImages(String originalName) {
    this.originalName = originalName;
    if (originalName.equals("default_image.jpg")) {
      this.uniqueName = "default_image.jpg";
    } else {
      this.uniqueName = generateUniqueName(extractExtension(originalName));
    }
  }

  private String generateUniqueName(String extension) {
    return UUID.randomUUID() + "." + extension;
  }

  private String extractExtension(String originName) {
    String ext = originName.substring(originName.lastIndexOf(".") + 1);
    if (isSupportedFormat(ext)) {
      return ext;
    } else {
      throw new CustomException(ExceptionStatus.UNSUPPORTED_IMAGE_TYPE);
    }
  }

  private boolean isSupportedFormat(String ext) {
    return Arrays.stream(supportedExtension).anyMatch(e -> e.equalsIgnoreCase(ext));
  }

}
