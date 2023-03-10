package gladiator.philosopher.post.controller;

import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.service.CategoryService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.entity.PageRequest;
import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostModifyRequestDto;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.service.PostService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

  private final PostService postService;
  private final S3Uploader s3Uploader;
  private final String dirName = "postImg";
  private final CategoryService categoryService;

  /**
   * κ²μκΈ μμ± ( μλ£ )
   *
   * @param multipartFiles
   * @param postRequestDto
   * @param accountDetails
   */
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public Long createPost(
      final @RequestPart(required = false, value = "image") List<MultipartFile> multipartFiles,
      final @RequestPart("dto") PostRequestDto postRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    s3Uploader.checkFileUpload(multipartFiles);
//    List<String> urls = s3Uploader.upLoadFileToMulti(multipartFiles, dirName);
    List<String> urls = s3Uploader.uploadResizerTest(multipartFiles, dirName);
    Category category = categoryService.getCategoryEntity(postRequestDto.getCategory());
    Long postId = postService.createPost(urls, postRequestDto, accountDetails.getAccount(), category);
    return postId;
  }

  /**
   * κ²μλ¬Ό λ¨κ±΄ μ‘°ν ( μλ£ )
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponseDto getPost(@PathVariable("id") final Long id) {
    return postService.getPost(id);
  }

  /**
   * κ²μκΈ κ²μ ( μλ£ )
   *
   * @param condition
   * @param pageRequest
   * @return
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public MyPage<PostResponseDtoByQueryDsl> searchPost(
      final PostSearchCondition condition,
      final PageRequest pageRequest) {
    Pageable pageable = pageRequest.of();
    return postService.searchPostByCondition(condition, pageable);
  }

  /**
   * κ²μκΈ μ­μ  ( μλ£ )
   *
   * @param postId
   * @param accountDetails
   */
  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public void deletePost(
      final @PathVariable Long postId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    postService.deletePost(postId, accountDetails.getAccount());
  }

  /**
   * κ²μκΈ μμ 
   *
   * @param postId
   * @param postModifyRequestDto
   * @param accountDetails
   * @return
   */
  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
  public Long modifyPost(
      final @PathVariable Long postId,
      final @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
      final @RequestPart("dto") PostModifyRequestDto postModifyRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    List<String>url = new ArrayList<>();
    if(s3Uploader.checkFileExist(multipartFiles)){ // νμΌμ μλ‘λ νμ§ μμλ (""κ°μΌλ‘ λ€μ΄μ΄ default κ°)
      Category category = categoryService.getCategoryEntity(postModifyRequestDto.getCategoryId());
      Long post = postService.modifyPost(postId, url, postModifyRequestDto, accountDetails.getAccount(), category);
      return post;
    }else{
      s3Uploader.checkFileUpload(multipartFiles); // νμΌμ μλ‘λ ν  λ, μ¦. μ΄λ―Έμ§κ° μλ€λ λ§.
      List<String> strings = s3Uploader.upLoadFileToMulti(multipartFiles, dirName);
      List<String> oldUrls = postService.getOldUrls(postId);
      Category category = categoryService.getCategoryEntity(postModifyRequestDto.getCategoryId());
      Long post = postService.modifyPost(postId, strings, postModifyRequestDto, accountDetails.getAccount(), category);
      s3Uploader.DeleteS3Files(oldUrls, dirName);
      return post;
    }
  }
}




