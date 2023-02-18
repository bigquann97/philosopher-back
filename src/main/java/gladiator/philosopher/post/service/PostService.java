package gladiator.philosopher.post.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
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
  Long createPost(List<String> urls, PostRequestDto postRequestDto,
      Account accountDetails, Category category);

  /**
   * 게시물 단건 조회
   *
   * @param postId
   * @return
   */
  PostResponseDto getPost(Long postId);

//  List<PostsResponseDto> searchPostByCondition(int pageChoice);

  Long modifyOnlyPost(Long postId, PostRequestDto postRequestDto, Account account);
  Long modifyPostAndImage(Long postId, List<String> urls, PostRequestDto postRequestDto, Account account);

  void deletePost(Long postId, Account accountDetails);

  Post getPostEntity(Long postId);

  void deletePostByAdmin(Long id);
  Long modifyPostByAdmin(Long id, PostRequestDto postRequestDto);
  Page<PostsResponseDto> searchPostByCondition(PostSearchCondition condition, Pageable pageable);
  List<String> getOldUrls(Long id);
}
