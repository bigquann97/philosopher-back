package gladiator.philosopher.post.service;

import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  PostResponseDto getPost(Long postId);

  List<PostsResponseDto> getPosts(int pageChoice);

  void createPost(List<MultipartFile> multipartFiles, PostRequestDto postRequestDto,
      AccountDetails accountDetails);

  PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      AccountDetails accountDetails);

  void deletePost(Long postId, AccountDetails accountDetails);

  Post getPostEntity(Long postId);


  void deletePostByAdmin(Long id);

  void modifyPostByAdmin(Long id, PostRequestDto postRequestDto);
}
