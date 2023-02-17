package gladiator.philosopher.post.controller;

import gladiator.philosopher.account.repository.AccountInfoRepository;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.category.service.CategoryService;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.post.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Slf4j
public class PostController {

  private final AccountInfoRepository accountInfoRepository;

  private final PostService postService;
  private final S3Uploader s3Uploader;
  private final String dirName = "postImg";
  private final CategoryService categoryService;

  // /api/posts
  @PostMapping(
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}
  )
  @ResponseStatus(HttpStatus.OK)
  public void createPost(
      final @RequestPart("image") List<MultipartFile> multipartFiles,
      final @RequestPart("dto") PostRequestDto postRequestDto,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    List<String> FailToPostUrls = null;
    postRequestDto.checkByOpinionCount();
    s3Uploader.checkFilesExtension(multipartFiles);
    List<String> urls = s3Uploader.upLoadFileToMulti(multipartFiles, dirName);
    FailToPostUrls = urls.stream().collect(Collectors.toList());
    Category Category = categoryService.getCategoryEntity(postRequestDto.getCategory());
    try {
      postService.createPost(urls, postRequestDto, accountDetails.getAccount(), Category);
    } catch (Exception e) {
      for (String url : FailToPostUrls) {
        s3Uploader.newDeleteS3(url, dirName);
      }
      throw new CustomException(ExceptionStatus.FAIL_TO_POSTING);
    }
  }

  // /api/posts?page=1
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostsResponseDto> getPosts(final @RequestParam int page) {
    return postService.SearchByQuerydsl(page);
  }

  @GetMapping("/")
  public List<TestPostResponseDto> search(
      final PostSearchCondition condition,
      final Pageable pageable) {
    return postService.SearchByQuerydsl(condition, pageable);
  }

  // /api/posts/1
  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponseDto getPost(final @PathVariable Long postId) {
    return postService.getPost(postId);
  }

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
        postService.modifyPostAndImage(postId, strings, postRequestDto, accountDetails.getAccount());
        s3Uploader.DeleteS3Files(oldUrls,dirName);
      } catch (RuntimeException e) {
        s3Uploader.DeleteS3Files(strings,dirName);
      }
    }
    return postId;
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(
      final @PathVariable Long postId,
      final @AuthenticationPrincipal AccountDetails accountDetails
  ) {
    postService.deletePost(postId, accountDetails.getAccount());
  }
}


