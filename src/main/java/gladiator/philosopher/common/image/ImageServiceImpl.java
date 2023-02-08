package gladiator.philosopher.common.image;

import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import java.io.File;
import java.io.IOException;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

  @Value("${upload.image.location}")
  private String location;

  @PostConstruct
  void postConstruct() {
    File dir = new File(location);
    if (!dir.exists()) {
      dir.mkdir();
    }
  }

  @Override
  public void upload(MultipartFile file, String filename) {
    try {
      file.transferTo(new File(location + filename));
    } catch (IOException e) {
      throw new CustomException(ExceptionStatus.IMAGE_UPLOAD_FAILED);
    }
  }

  @Override
  public void delete(String filename) {
    new File(location + filename).delete();
  }

}
