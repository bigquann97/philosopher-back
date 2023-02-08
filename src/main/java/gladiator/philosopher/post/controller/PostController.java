package gladiator.philosopher.post.controller;

import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;
//  @PostMapping
//  public ResponseEntity<PostResponseDto> newPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal MemberDetails memberDetails){
//    postService
//    return ResponseEntity.status(200).body(postService.getPost(postId));
//  }

  @GetMapping("/{postId}")
  public ResponseEntity<PostResponseDto> getPost(@PathVariable Long postId) {
    return ResponseEntity.status(200).body(postService.getPost(postId));
  }
}

