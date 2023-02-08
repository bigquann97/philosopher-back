package gladiator.philosopher.post.service;

import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;

public interface PostService {

  PostResponseDto getPost(Long postId);

  List<PostResponseDto> getPosts(int pageChoice);

  PostResponseDto newPost(PostRequestDto postRequestDto, MemberDetails memberDetails);

  PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      MemberDetails memberDetails);

  void deletePost(Long postId, MemberDetails memberDetails);
}
