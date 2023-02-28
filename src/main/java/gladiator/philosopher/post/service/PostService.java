package gladiator.philosopher.post.service;

import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.dto.PostModifyRequestDto;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PostService {
  // CRUD
  Long createPost(List<String> urls, PostRequestDto postRequestDto, Account accountDetails, Category category);

  PostResponseDto getPost(Long postId);

  MyPage<PostResponseDtoByQueryDsl> searchPostByCondition(PostSearchCondition condition, Pageable pageable);

  void deletePost(Long postId, Account accountDetails);

  Long modifyPost(Long postId,List<String>urls, PostModifyRequestDto postModifyRequestDto, Account account, Category category);

  Long modifyOnlyPost(Long postId, PostRequestDto postRequestDto, Account account);

  // GET DATA
  Post getPostEntity(Long postId);

  List<String> getOldUrls(Long id);

  List<PostImage> getPostImages(Post post);

  List<PostOpinion> getPostOpinions(Post post);

  MyPage<PostSimpleResponseDto> getMyPosts(Long accountId, Pageable pageable);

  MyPage<PostSimpleResponseDto> getRecommendPostsByAccount(Long accountId, Pageable pageable);
}
