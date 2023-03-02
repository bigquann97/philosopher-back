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
  Long createPost(
      final List<String> urls,
      final PostRequestDto postRequestDto,
      final Account accountDetails,
      final Category category
  );

  PostResponseDto getPost(final Long postId);

  MyPage<PostResponseDtoByQueryDsl> searchPostByCondition(
      final PostSearchCondition condition,
      final Pageable pageable
  );

  void deletePost(
      final Long postId,
      final Account accountDetails
  );

  Long modifyPost(final Long postId,List<String>urls,
      final PostModifyRequestDto postModifyRequestDto,
      final Account account,
      final Category category);

  // GET DATA
  Post getPostEntity(final Long postId);

  List<String> getOldUrls(final Long id);

  List<PostImage> getPostImages(final Post post);

  List<PostOpinion> getPostOpinions(final Post post);

  MyPage<PostSimpleResponseDto> getMyPosts
      (
      final Long accountId,
      final Pageable pageable
      );

  MyPage<PostSimpleResponseDto> getRecommendPostsByAccount
      (
          final Long accountId,
          final Pageable pageable
      );
}
