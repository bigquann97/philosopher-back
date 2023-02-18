package gladiator.philosopher.post.controller;

import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.service.CategoryService;
import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
   * 게시글 작성 ( 완료 )
   *
   * @param multipartFiles
   * @param postRequestDto
   * @param accountDetails
   */
  @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public Long createPost(
      final @RequestPart(required = false, value = "image") List<MultipartFile> multipartFiles,
      final @RequestPart("dto") PostRequestDto postRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    List<String> urls = s3Uploader.upLoadFileToMulti(multipartFiles, dirName);
    System.out.println(urls);
      Category Category = categoryService.getCategoryEntity(postRequestDto.getCategory());
      final Long postId = postService.createPost(urls, postRequestDto, accountDetails.getAccount(), Category);
      return postId;
  }


  /**
   * 게시글 검색
   *
   * @param condition
   * @param pageable
   * @return
   */
  @GetMapping("/")
  public Page<PostsResponseDto> searchPost(
      final PostSearchCondition condition,
      final Pageable pageable) {
    return postService.searchPostByCondition(condition, pageable);
  }

  /**
   * 게시글 수정
   *
   * @param postId
   * @param postRequestDto
   * @param accountDetails
   * @return
   */
  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Long modifyPost(
      final @PathVariable Long postId,
      final @RequestBody PostRequestDto postRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    final Long PostId = postService.modifyOnlyPost(postId, postRequestDto,
        accountDetails.getAccount());
    return PostId;
  }

  /**
   * 게시글 삭제
   *
   * @param postId
   * @param accountDetails
   */
  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(
      final @PathVariable Long postId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    postService.deletePost(postId, accountDetails.getAccount());
  }


  @PutMapping("/test/{postId}")
  @ResponseStatus(HttpStatus.CREATED)
  public Long modifyPostImages(
      final @PathVariable Long postId,
      final @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles,
      final @RequestPart("dto") PostRequestDto postRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    if (multipartFiles.get(0).getOriginalFilename().equals("")) {
      postService.modifyOnlyPost(postId, postRequestDto, accountDetails.getAccount());
    } else {
      List<String> oldUrls = postService.getOldUrls(postId);
      s3Uploader.checkFilesExtension(multipartFiles);
      final List<String> strings = s3Uploader.upLoadFileToMulti(multipartFiles, dirName);
      try {
        postService.modifyPostAndImage(postId, strings, postRequestDto,
            accountDetails.getAccount());
        s3Uploader.DeleteS3Files(oldUrls, dirName);
      } catch (RuntimeException e) {
        s3Uploader.DeleteS3Files(strings, dirName);
      }
    }
    return postId;
  }


}


