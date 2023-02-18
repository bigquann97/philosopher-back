package gladiator.philosopher.post.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

  // CRUD
  Long createPost(List<String> urls, PostRequestDto postRequestDto, Account accountDetails, Category category); // 게시물 작성 ( 완료 - C)

  PostResponseDto getPost(Long postId); // 게시물 단건 조회 ( 완료 - R )
  Page<PostResponseDtoByQueryDsl> searchPostByCondition(PostSearchCondition condition, Pageable pageable); // 게시물 조건조회 ( 완료 - R) - 아직 관호님꺼 적용 x
  void deletePost(Long postId, Account accountDetails); // 게시물 삭제 ()
  Long modifyOnlyPost(Long postId, PostRequestDto postRequestDto, Account account);

  Long modifyPostAndImage(Long postId, List<String> urls, PostRequestDto postRequestDto,
      Account account);

  Long modifyPostByAdmin(Long id, PostRequestDto postRequestDto); // 게시글 수정 ( )

  // GET DATA
  Post getPostEntity(Long postId);

  void deletePostByAdmin(Long id);

  Long modifyPostByAdmin(Long id, PostRequestDto postRequestDto);

  Page<PostsResponseDto> searchPostByCondition(PostSearchCondition condition, Pageable pageable);

  List<String> getOldUrls(Long id);

  List<PostImage> getPostImages(Post post);

  List<PostOpinion> getPostOpinions(Post post);
}
