package gladiator.philosopher.post.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_POST;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.post.dto.PostModifyRequestDto;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostOpinionRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.recommend.repository.PostRecommendRepository;
import gladiator.philosopher.report.repository.PostReportRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

  private final PostReportRepository postReportRepository;

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
      saveImages(url, post);
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
  public MyPage<PostResponseDtoByQueryDsl> searchPostByCondition(
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
    post.changeStatusDeleteByAdmin();
    postRepository.saveAndFlush(post);
  }

  @Override
  @Transactional
  public Long modifyPost(final Long postId, final List<String> urls, final PostModifyRequestDto postModifyRequestDto,
      final Account account, final Category category) {
    Post post = getPostEntity(postId);
    log.info(post.getTitle());
    post.isWriter(account);
    post.modifyPost(postModifyRequestDto.getTitle(), postModifyRequestDto.getContent(), category);
    postRepository.saveAndFlush(post);
    if (urls == null) {
      return post.getId();
    } else {
      saveImages(urls, post);
      postImageRepository.deleteAllByPostImage(post.getId());
      return post.getId();
    }
  }

  /**
   * 의견 저장 -> 사용처 : createPost
   *
   * @param postRequestDto
   * @param post
   */
  private void saveOpinions(final PostRequestDto postRequestDto, final Post post) {
    List<PostOpinion> opinions = postRequestDto.getOpinions().stream()
        .map(x -> new PostOpinion(post, x)).collect(Collectors.toList());
    postOpinionRepository.saveAll(opinions);
  }

  /**
   * 이미지 저장 -> 사용처 : createPost, modifyPost
   *
   * @param url
   * @param post
   */
  @Transactional
  public void saveImages(final List<String> url, final Post post) {
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

  /**
   * 게시글 삭제 ( 상태 변경 )- 사용처 : 어드민
   *
   * @param postId
   */
  @Override
  @Transactional
  public void deletePostByAdmin(final Long postId) {
    final Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(NOT_FOUND_POST));
    post.changeStatusDeleteByAdmin();

    postRepository.saveAndFlush(post);
  }


  /**
   * ID를 이용한 Post 객체 찾기
   *
   * @param postId
   * @return
   */
  @Override
  public Post getPostEntity(final Long postId) {
    return postRepository.findById(postId).orElseThrow(
        () -> new CustomException(NOT_FOUND_POST)
    );
  }

  /**
   * 게시글 수정 - 사용처 : 어드민
   *
   * @param postId
   * @param postRequestDto
   * @return
   */
  @Override
  @Transactional
  public Long modifyPostByAdmin(final Long postId, final PostRequestDto postRequestDto) {
    Post post = getPostEntity(postId);
    post.modifyPost(postRequestDto.getTitle(), postRequestDto.getContent());
    postRepository.save(post);
    return post.getId();
  }


  @Override
  @Transactional(readOnly = true)
  public List<String> getOldUrls(final Long id) {
    return postImageRepository.getUrl(getPostEntity(id).getId());
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostImage> getPostImages(final Post post) {
    return postImageRepository.findByPost(post);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostOpinion> getPostOpinions(final Post post) {
    return postOpinionRepository.findByPost(post);
  }


}
