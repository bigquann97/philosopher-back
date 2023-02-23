package gladiator.philosopher.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import gladiator.philosopher.common.exception.FileException;
import gladiator.philosopher.common.exception.dto.ExceptionStatus;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  private final static String Extension[] = {"jpg", "jpeg", "png", ""};

  /**
   * 단일 파일 업로드
   *
   * @param multipartFile
   * @param dirName
   * @return
   * @throws IOException
   */
  public String upLoadFileToSingle(MultipartFile multipartFile, String dirName) {
    try {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(multipartFile.getSize());
      objectMetadata.setContentType(multipartFile.getContentType());

      String fileName =
          dirName + "/" + UUID.randomUUID() + multipartFile.getName(); // s3에 저장될 파일의 이름
      String uploadImageUrl = putS3(multipartFile.getInputStream(), fileName,
          objectMetadata); // s3로 업로드
      log.info("url : " + uploadImageUrl);
      return uploadImageUrl;
    } catch (IOException e) {
      throw new FileException(ExceptionStatus.IMAGE_UPLOAD_FAILED);
    }
  }

  /**
   * 다중 파일 업로드
   *
   * @param multipartFiles
   * @param dirName
   * @return
   * @throws IOException
   */
  public List<String> upLoadFileToMulti(List<MultipartFile> multipartFiles, String dirName) {

    if (multipartFiles.get(0).getOriginalFilename().equals("")) {
      return null;
    }
    try {
      List<String> resultUrlList = new ArrayList<>();

      for (MultipartFile multipartFile : multipartFiles) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
        String fileName =
            dirName + "/" + UUID.randomUUID() + multipartFile.getName(); // s3에 저장될 파일의 이름
        String uploadImageUrl = putS3(multipartFile.getInputStream(), fileName,
            objectMetadata); // s3로 업로드
        log.info("upfile url is :" + uploadImageUrl);
        resultUrlList.add(uploadImageUrl);
      }
      return resultUrlList;
    } catch (IOException e) {
      throw new FileException(ExceptionStatus.IMAGE_UPLOAD_FAILED);
    }

  }

  public List<String> uploadResizerTest(List<MultipartFile> multipartFiles, String dirName) {
    if (multipartFiles.get(0).getOriginalFilename().equals("")) {
      return null;
    }
    try {
      log.info("업로드 시작");
      List<String> imageList = new ArrayList<>(); // 리사이징 된 이미지를 저장할 공간

      for (MultipartFile multipartFile : multipartFiles) {
        String fileName = dirName + "/" + UUID.randomUUID() + multipartFile.getName(); // s3에 저장될 파일의 이름
        String fileFormat = multipartFile.getContentType().substring(multipartFile.getContentType().lastIndexOf("/")+1);
        MultipartFile resizer = resizer(fileName, fileFormat, multipartFile, 890);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(resizer.getSize());
        objectMetadata.setContentType(resizer.getContentType());
        String uploadUrl = putS3(resizer.getInputStream(), fileName, objectMetadata);
        log.info("upfile url is :" + uploadUrl);
        imageList.add(uploadUrl);
      }
      return imageList;
    }catch (IOException e) {
      throw new FileException(ExceptionStatus.IMAGE_UPLOAD_FAILED);
    }
  }

  @Transactional
  public MultipartFile resizer(String fileName, String fileFormat, MultipartFile multipartFile,
      int width) {
    log.info("이미지 리사이징 시작");
    try {
      BufferedImage image = ImageIO.read(multipartFile.getInputStream());
      log.info("기존의 데이터 사이즈(가로)는 다음과 같습니다." + image.getWidth());
      int originWidth = image.getWidth();
      log.info("기존의 데이터 사이즈(세로)는 다음과 같습니다." + image.getHeight());
      int originHeight = image.getHeight();

      if (originWidth < width) {
        return multipartFile;
      }
      MarvinImage imageMarvin = new MarvinImage(image);

      Scale scale = new Scale();
      scale.load();
      scale.setAttribute("newWidth", width);
      scale.setAttribute("newHeight", width * originHeight / originWidth);
      scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

      BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(imageNoAlpha, fileFormat, baos);
      baos.flush();

      return new CustomMultipartFile(fileName, fileFormat, multipartFile.getContentType(),
          baos.toByteArray());
    } catch (IOException e) {
      throw new FileException(ExceptionStatus.FAIL_TO_RESIZE_FILE);
    }
  }


  public List<String> getFilesUrl(List<MultipartFile> multipartFiles, String dirName) {
    List<String> resultUrls = new ArrayList<>();

    for (MultipartFile file : multipartFiles) {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(file.getSize());
      objectMetadata.setContentType(MediaType.IMAGE_JPEG_VALUE);
      String fileName =
          dirName + "/" + UUID.randomUUID() + file.getName(); // s3에 저장될 파일의 이름
      resultUrls.add(fileName);
      log.info(fileName);
    }
    return resultUrls;
  }

  /**
   * S3 업로드
   *
   * @param file
   * @param fileName
   * @param objectMetadata
   * @return
   */
  private String putS3(InputStream file, String fileName, ObjectMetadata objectMetadata) {
    amazonS3Client.putObject(
        new PutObjectRequest(bucket, fileName, file, objectMetadata).withCannedAcl(
            CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  /**
   * S3 단일 파일 삭제
   *
   * @param url
   * @param dirName
   */
  public void newDeleteS3(String url, String dirName) {
    final String[] split = url.split("/");
    final String fileName = dirName + "/" + split[split.length - 1];
    DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
    log.info("delete url : " + request);
    amazonS3Client.deleteObject(request);
  }

  /**
   * S3 다중 파일 삭제
   *
   * @param urls
   * @param dirName
   */
  public void DeleteS3Files(List<String> urls, String dirName) {
    for (String url : urls) {
      final String[] split = url.split("/");
      final String fileName = dirName + "/" + split[split.length - 1];
      DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
      log.info("delete url : " + request);
      amazonS3Client.deleteObject(request);
    }
  }


  /**
   * 단일 파일 확장자 검사
   *
   * @param file
   * @return
   */
  public void checkFileExtension(MultipartFile file) {
    int index = file.getOriginalFilename().lastIndexOf(".");
    String substring = file.getOriginalFilename().substring(index + 1);
    boolean b = Arrays.stream(Extension).anyMatch(check -> check.equalsIgnoreCase(substring));
    System.out.println(b);
    if (!b) {
      throw new FileException(ExceptionStatus.UNSUPPORTED_IMAGE_TYPE);
    }
  }

  /**
   * 다중 파일 확장자 검사
   *
   * @param files
   * @return
   */
  public void checkFilesExtension(
      List<MultipartFile> files) { // 이 부분 이대로 사용할 것인지, 아니면 수정할 것인지 고민해보기
    List<String> extension = new ArrayList<>();
    boolean result = true;

    files.forEach(multipartFile -> {
      int index = multipartFile.getOriginalFilename().lastIndexOf(".");
      extension.add(multipartFile.getOriginalFilename().substring(index + 1));
    });
    for (String check : extension) {
      String checkCase = check.toLowerCase(Locale.ROOT);
      switch (checkCase) {
        case "jpg":
          break;
        case "png":
          break;
        case "jpeg":
          break;
        case "":
          break;
        default:
          result = false;
          break;
      }
    }
    if (!result) {
      throw new FileException(ExceptionStatus.UNSUPPORTED_IMAGE_TYPE);
    }
  }

  public void checkByFileCount(List<MultipartFile> multipartFiles) {
    if (multipartFiles.size() > 4) {
      throw new FileException(ExceptionStatus.TO_MUCH_FILES);
    }
  }

  public void checkFileUpload(List<MultipartFile> multipartFiles) {
    checkByFileCount(multipartFiles); // 파일 갯수 확인
    checkFilesExtension(multipartFiles); // 파일 확장자 검사
  }




}