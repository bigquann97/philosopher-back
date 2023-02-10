package gladiator.philosopher.post.controller;

import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
public class PostController {

  private final PostService postService;
  // /api/posts
  @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.OK)
  public void createPost(
      @RequestPart("image") List<MultipartFile> multipartFiles,
      @RequestPart("dto") PostRequestDto postRequestDto,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    postService.createPost(multipartFiles, postRequestDto, memberDetails);
  }

  // /api/posts?page=1
  @GetMapping
  public List<PostResponseDto> getPosts(@RequestParam int page) {
    return postService.getPosts(page);
  }

  // /api/posts/1
  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
    return ResponseEntity.status(200).body(postService.getPost(postId));
  }

  @PutMapping("/{postId}")
  public ResponseEntity<PostResponseDto> modifyPost(@PathVariable Long postId,
      @RequestBody PostRequestDto postRequestDto,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    return ResponseEntity.status(200)
        .body(postService.modifyPost(postId, postRequestDto, memberDetails));
  }

  @DeleteMapping("/{postId}")
  public ResponseEntity<String> deletePost(@PathVariable Long postId,
      @AuthenticationPrincipal MemberDetails memberDetails) {
    postService.deletePost(postId, memberDetails);
    return ResponseEntity.status(200).body("삭제 완료");
  }

}

