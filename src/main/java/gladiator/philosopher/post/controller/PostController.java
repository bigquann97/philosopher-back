package gladiator.philosopher.post.controller;

import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class PostController {

  private final PostService postService;

  // /api/posts
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public void createPost(
      @RequestPart("image") List<MultipartFile> multipartFiles,
      @RequestPart("dto") PostRequestDto postRequestDto,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    postService.createPost(multipartFiles, postRequestDto, memberDetails);
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
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return postService.modifyPost(postId, postRequestDto, memberDetails);
  }

  @DeleteMapping("/{postId}")
  @ResponseStatus(HttpStatus.OK)
  public void deletePost(@PathVariable Long postId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    postService.deletePost(postId, memberDetails);
  }
}

