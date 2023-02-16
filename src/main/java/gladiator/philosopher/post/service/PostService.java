package gladiator.philosopher.post.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PostService {

  /**
   * 게시글 생성
   *
   * @param urls
   * @param postRequestDto
   * @param accountDetails
   * @param category
   */
  void createPost(List<String> urls, PostRequestDto postRequestDto,
      Account accountDetails, Category category);

  /**
   * 게시물 단건 조회
   *
   * @param postId
   * @return
   */
  PostResponseDto getPost(Long postId);

  List<PostsResponseDto> SearchByQuerydsl(int pageChoice);

  PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      Account accountDetails);

  void deletePost(Long postId, Account accountDetails);

  Post getPostEntity(Long postId);

  void deletePostByAdmin(Long id);

  void modifyPostByAdmin(Long id, PostRequestDto postRequestDto);

  //  List<PostResponseDto> searchPost(PostSearchCondition condition, Pageable pageable);

  List<TestPostResponseDto> SearchByQuerydsl(PostSearchCondition condition, Pageable pageable);

//  List<TestPostResponseDto> getPostAndAccount(Long id);
// postId만 필요할 경우 postId 존재 확인 후 postId를 반환

}
