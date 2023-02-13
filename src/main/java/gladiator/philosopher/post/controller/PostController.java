package gladiator.philosopher.post.controller;

import gladiator.philosopher.common.s3.S3Uploader;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  private final PostService postService;
  private final S3Uploader s3Uploader;
  private final String driName = "postImg";

  // /api/posts
  @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,
      MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public void createPost(
      @RequestPart("image") List<MultipartFile> multipartFiles,
      @RequestPart("dto") PostRequestDto postRequestDto,
      @AuthenticationPrincipal AccountDetails accountDetails) {
//    log.info("file : " + multipartFiles);
//
//    try {
//      List<String> urlList = s3Uploader.upLoadFile(multipartFiles, driName);
//
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }

    postService.createPost(multipartFiles, postRequestDto, accountDetails);

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

  @GetMapping("/test/{id}")
  public ResponseEntity<List<TestPostResponseDto>> startTest(@PathVariable("id") Long id) {
    return ResponseEntity.status(200).body(postService.getPostAndAccount(id));
  }

}

