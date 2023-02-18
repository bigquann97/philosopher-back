package gladiator.philosopher.post.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_POST;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostOpinionRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.recommend.repository.PostRecommendRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;
  private final PostOpinionRepository postOpinionRepository;
  private final PostRecommendRepository postRecommendRepository;

  @Override
  @Transactional
  public Long createPost(
      final List<String> url,
      final PostRequestDto postRequestDto,
      final Account account,
      final Category category
  ) {
    postRequestDto.checkByOpinionCount();
    Post post = postRequestDto.toEntity(account, category);
    if (url == null) {
      saveOpinions(postRequestDto, post);
      postRepository.save(post);
    } else {
      saveImages(url,post);
      saveOpinions(postRequestDto, post);
      postRepository.save(post);
    }
    return post.getId();
  }


  @Override
  @Transactional(readOnly = true)
  public PostResponseDto getPost(final Long postId) {
    Post post = getPostEntity(postId);
    final long recommendCount = postRecommendRepository.countByPost(post);
    final List<String> url = postImageRepository.getUrl(post.getId());
    return new PostResponseDto(post, recommendCount, url);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PostResponseDtoByQueryDsl> searchPostByCondition(
      final PostSearchCondition condition,
      final Pageable pageable
  ) {
    return postRepository.searchPost(condition, pageable);
  }

  @Override
  @Transactional
  public void deletePost(final Long postId, final Account account) {
    Post post = getPostEntity(postId);
    post.isWriter(account);
    postOpinionRepository.deleteAllByPostOpinion(post);
    postImageRepository.deleteAllByPostImage(post);
    postRecommendRepository.deleteAllByPostRecommend(post);
    postRepository.delete(post);
  }


  /**
   * 의견 저장 -> 사용처 : createPost
   * @param postRequestDto
   * @param post
   */
  private void saveOpinions(PostRequestDto postRequestDto, Post post){
    List<PostOpinion> opinions = postRequestDto.getOpinions().stream()
        .map(x -> new PostOpinion(post, x)).collect(Collectors.toList());
    postOpinionRepository.saveAll(opinions);
  }

  /**
   * 이미지 저장 -> 사용처 : createPost
   * @param url
   * @param post
   */
  private void saveImages(List<String> url, Post post){
    for (String s : url) {
      PostImage postImage = new PostImage(s, post);
      postImageRepository.save(postImage);
    }
  }


  @Override
  @Transactional
  public Long modifyOnlyPost(
      final Long postId,
      final PostRequestDto postRequestDto,
      final Account account
  ) {
    Post post = getPostEntity(postId);
    post.isWriter(account);
    post.modifyPost(postRequestDto.getTitle(), postRequestDto.getContent());
    postRepository.save(post);
    return post.getId();
  }



  @Override // 여기서 필요한 작업은 -> 해당 DB단말고 파일 데이터도 지워야 함
  public void deletePostByAdmin(final Long postId) {
    Post post = getPostEntity(postId);
    postRepository.delete(post);
  }


  @Override
  public Post getPostEntity(final Long postId) {
    return postRepository.findById(postId).orElseThrow(
        () -> new CustomException(NOT_FOUND_POST)
    );
  }

  @Override
  @Transactional
  public Long modifyPostByAdmin(final Long postId, final PostRequestDto postRequestDto) {
    Post post = getPostEntity(postId);
    post.modifyPost(postRequestDto.getTitle(), postRequestDto.getContent());
    postRepository.save(post);
    return post.getId();
  }



  @Override
  public List<String> getOldUrls(Long id) {
    return postImageRepository.getUrl(getPostEntity(id).getId());
  }

  @Override
  public List<PostImage> getPostImages(Post post) {
    return postImageRepository.findByPost(post);
  }

  @Override
  public List<PostOpinion> getPostOpinions(Post post) {
    return postOpinionRepository.findByPost(post);
  }

  @Override
  @Transactional
  public Long modifyPostAndImage(Long postId, List<String> urls, PostRequestDto postRequestDto,
      Account account) {
    Post post = getPostEntity(postId);
    post.isWriter(account);
    post.modifyPost(postRequestDto.getTitle(), postRequestDto.getContent());
    postRepository.save(post);
    postImageRepository.deleteAllByPostImage(post);
    for (String url : urls) {
      PostImage postImage = new PostImage(url, post);
      postImageRepository.save(postImage);
    }
    return postId;
  }

}
