package gladiator.philosopher.post.service;

import gladiator.philosopher.post.dto.PostResponseDto;

public interface PostService {

  PostResponseDto getPost(Long postId);

}
