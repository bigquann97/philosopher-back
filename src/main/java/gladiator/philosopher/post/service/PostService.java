package gladiator.philosopher.post.service;

import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  PostResponseDto getPost(Long postId);

  List<PostResponseDto> getPosts(int pageChoice);

  void createPost(List<MultipartFile> multipartFiles, PostRequestDto postRequestDto,
      MemberDetails memberDetails);

  PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      MemberDetails memberDetails);

  void deletePost(Long postId, MemberDetails memberDetails);

  Post getPostEntity(Long postId);


  void deletePostByAdmin(Long id);
  void modifyPostByAdmin(Long id, PostRequestDto postRequestDto);
}
