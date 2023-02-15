package gladiator.philosopher.post.service;

import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  PostResponseDto getPost(Long postId);

  @Transactional
  void createPost(List<MultipartFile> multipartFiles, PostRequestDto postRequestDto,
      AccountDetails accountDetails);

  List<PostsResponseDto> getPosts(int pageChoice);

  void createPost(List<String> urls, PostRequestDto postRequestDto,
      AccountDetails accountDetails, Category category);

  PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      AccountDetails accountDetails);

  void deletePost(Long postId, AccountDetails accountDetails);

  Post getPostEntity(Long postId);


  void deletePostByAdmin(Long id);

  void modifyPostByAdmin(Long id, PostRequestDto postRequestDto);

  //  List<PostResponseDto> searchPost(PostSearchCondition condition, Pageable pageable);
  List<String> getgetget(Long id);

  List<TestPostResponseDto> getPosts(PostSearchCondition condition, Pageable pageable);

//  List<TestPostResponseDto> getPostAndAccount(Long id);
// postId만 필요할 경우 postId 존재 확인 후 postId를 반환

}
