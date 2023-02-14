package gladiator.philosopher.common.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;
  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  private final static String Extension[] = {"jpg","jpeg","png",""};

  /**
   * 단일 파일 업로드
   * @param multipartFile
   * @param dirName
   * @return
   * @throws IOException
   */
  public String upLoadFileToSingle(MultipartFile multipartFile, String dirName)throws IOException {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(multipartFile.getSize());
    objectMetadata.setContentType(multipartFile.getContentType());

    String fileName = dirName + "/" + UUID.randomUUID() + multipartFile.getName(); // s3에 저장될 파일의 이름
    String uploadImageUrl = putS3(multipartFile.getInputStream(), fileName,
        objectMetadata); // s3로 업로드
    log.info("url : " + uploadImageUrl);

    return uploadImageUrl;
  }

  /**
   * 다중 파일 업로드
   * @param multipartFiles
   * @param dirName
   * @return
   * @throws IOException
   */
  public List<String> upLoadFileToMulti(List<MultipartFile> multipartFiles, String dirName)
      throws IOException {
    List<String> resultUrlList = new ArrayList<>();

    for (MultipartFile multipartFile : multipartFiles) {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentLength(multipartFile.getSize());
      objectMetadata.setContentType(MediaType.IMAGE_JPEG_VALUE);

      String fileName = dirName + "/" + UUID.randomUUID() + multipartFile.getName(); // s3에 저장될 파일의 이름
      String uploadImageUrl = putS3(multipartFile.getInputStream(), fileName,
          objectMetadata); // s3로 업로드
      log.info("url : " + uploadImageUrl);

      resultUrlList.add(uploadImageUrl);

    }
    return resultUrlList;
  }


  /**
   * S3 업로드
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
   * S3 삭제
   * @param fileName
   */
  public void deleteS3(String fileName) {
    DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileName);
    amazonS3Client.deleteObject(request);
  }

  /**
   * 단일 파일 확장자 검사
   * @param file
   * @return
   */
  public void checkFileExtension(MultipartFile file){
    int index = file.getOriginalFilename().lastIndexOf(".");
    String substring = file.getOriginalFilename().substring(index + 1);
    boolean b = Arrays.stream(Extension).anyMatch(check -> check.equalsIgnoreCase(substring));
    System.out.println(b);
    if(!b)
      throw new CustomException(ExceptionStatus.UNSUPPORTED_IMAGE_TYPE);
  }

  /**
   * 다중 파일 확장자 검사
   * @param files
   * @return
   */
  public void checkFilesExtension(List<MultipartFile> files) { // 이 부분 이대로 사용할 것인지, 아니면 수정할 것인지 고민해보기
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
    if(!result)
      throw new CustomException(ExceptionStatus.UNSUPPORTED_IMAGE_TYPE);
  }

}