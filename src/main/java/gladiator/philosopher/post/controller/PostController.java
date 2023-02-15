package gladiator.philosopher.post.controller;

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
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.post.service.PostService;
import java.io.IOException;
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

  private final PostRepository postRepository;

  private final PostService postService;
  private final S3Uploader s3Uploader;
  private final String dirName = "postImg";
  private final CategoryService categoryService;

  // /api/posts
  @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
      MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public void createPost(
      @RequestPart("image") List<MultipartFile> multipartFiles,
      @RequestPart("dto") PostRequestDto postRequestDto,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    List<String> FailToPostUrls = null;
    try {
      postRequestDto.checkByOpinionCount();
      s3Uploader.checkFilesExtension(multipartFiles);
      List<String> urls = s3Uploader.upLoadFileToMulti(multipartFiles, dirName);
      FailToPostUrls = urls.stream().collect(Collectors.toList());
      Category Category = categoryService.getCategoryEntity(postRequestDto.getCategory());
      postService.createPost(urls, postRequestDto, accountDetails, Category);
    } catch (IOException e) {
      for (String url : FailToPostUrls) {
        String[] split = url.split("/");
        String filename = dirName + "/" + split[split.length - 1];
        s3Uploader.deleteS3(filename);
      }
      throw new CustomException(ExceptionStatus.IMAGE_UPLOAD_FAILED);
    }
  }

  // /api/posts?page=1
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostsResponseDto> getPosts(@RequestParam int page) {
    return postService.getPosts(page);
  }

  // /api/posts/1
  @GetMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponseDto getPost(@PathVariable Long postId) {
    return postService.getPost(postId);
  }

  @PutMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public PostResponseDto modifyPost(@PathVariable Long postId,
      @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    return postService.modifyPost(postId, postRequestDto, accountDetails);
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(@PathVariable Long postId,
      @AuthenticationPrincipal AccountDetails accountDetails) {
    postService.deletePost(postId, accountDetails);
  }

//  @GetMapping("/test/{id}")
//  public ResponseEntity<List<TestPostResponseDto>> startTest(@PathVariable("id") Long id) {
//    return ResponseEntity.status(200).body(postService.getPostAndAccount(id));
//  }

  @GetMapping("/test")
  public List<TestPostResponseDto> searchQuerydslTest(PostSearchCondition condition,
      Pageable pageable) {
    List<TestPostResponseDto> testPostResponseDtos = postRepository.searchPost(condition,
        pageable);
    return testPostResponseDtos;
  }

  @GetMapping("/testv2")
  public List<TestPostResponseDto> gegegege(PostSearchCondition condition, Pageable pageable) {
    return postService.getPosts(condition, pageable);
  }

}

