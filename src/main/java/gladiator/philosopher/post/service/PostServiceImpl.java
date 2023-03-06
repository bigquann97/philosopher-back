package gladiator.philosopher.post.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_POST;

import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.account.service.AccountInfoService;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.util.badword.BadWordFiltering;
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
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostImageRepository postImageRepository;
  private final PostOpinionRepository postOpinionRepository;
  private final PostRecommendRepository postRecommendRepository;
  private final BadWordFiltering badWordFiltering;
  private final AccountInfoService accountInfoService;


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
    String filteredTitle = badWordFiltering.checkAndChange(postRequestDto.getTitle());
    String filteredContent = badWordFiltering.checkAndChange(postRequestDto.getContent());
    post.modifyPost(filteredTitle, filteredContent);
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
  public PostResponseDto getPost(final Long id) {
    Post post = getPostEntity(id);
    String accountImageUrl = accountInfoService.selectAccountImageUrl(post.getAccount().getId());
    final long recommendCount = postRecommendRepository.countByPost(post);
    final List<String> url = postImageRepository.getUrl(post.getId());
    final List<String> options = postOpinionRepository.getOptions(post.getId());
    return new PostResponseDto(post, recommendCount, url, options, accountImageUrl);
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
  public Long modifyPost(final Long postId, final List<String> urls,
      final PostModifyRequestDto postModifyRequestDto,
      final Account account, final Category category) {
    Post post = getPostEntity(postId);
    log.info(post.getTitle());
    post.isWriter(account);
    String filteredTitle = badWordFiltering.checkAndChange(postModifyRequestDto.getTitle());
    String filteredContent = badWordFiltering.checkAndChange(postModifyRequestDto.getContent());
    post.modifyPost(filteredTitle, filteredContent, category);
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


  /**
   * ID를 이용한 Post 객체 찾기
   *
   * @param postId
   * @return
   */
  @Override
  public Post getPostEntity(final Long postId) {
    return postRepository.findById(postId).orElseThrow(
        () -> new NotFoundException(NOT_FOUND_POST)
    );
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

  @Override
  @Transactional(readOnly = true)
  public MyPage<PostSimpleResponseDto> getMyPosts(
      final Long accountId,
      final Pageable pageable) {
    Page<PostSimpleResponseDto> getPosts = postRepository.getPostsByAccount(accountId, pageable);
    return new MyPage<>(getPosts);
  }

  @Override
  public MyPage<PostSimpleResponseDto> getRecommendPostsByAccount(
      final Long accountId,
      final Pageable pageable) {
    Page<PostSimpleResponseDto> results = postRecommendRepository.getAllPosts(accountId, pageable);
    return new MyPage<>(results);
  }
}
