package gladiator.philosopher.post.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import gladiator.philosopher.post.entity.PostOpinion;
import gladiator.philosopher.post.repository.PostImageRepository;
import gladiator.philosopher.post.repository.PostOpinionRepository;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.recommend.service.RecommendService;
import java.util.ArrayList;
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
  private final RecommendService recommendService;
  private final PostOpinionRepository postOpinionRepository;

  @Override
  @Transactional
  public void createPost(
      final List<String> url,
      final PostRequestDto postRequestDto,
      final Account account,
      final Category category
  ) {
    Post post = postRequestDto.toEntity(account, category);
    for (String s : url) {
      PostImage postImage = new PostImage(s, post);
      postImageRepository.save(postImage);
    }

    List<PostOpinion> opinions = postRequestDto.getOpinions().stream()
        .map(x -> new PostOpinion(post, x)).collect(Collectors.toList());
    postOpinionRepository.saveAll(opinions);

    postRepository.save(post);
  }

  @Override
  @Transactional
  public List<PostsResponseDto> SearchByQuerydsl(final int page) {
    Page<Post> posts = postRepository.findAll(pageableSetting(page));
    if (posts.isEmpty()) {
      throw new CustomException(ExceptionStatus.POST_IS_NOT_EXIST);
    }
    List<PostsResponseDto> PostResponseDtoList = posts.stream().map(PostsResponseDto::new).collect(
        Collectors.toList());
    return PostResponseDtoList;
  }

  private Pageable pageableSetting(final int pageChoice) {
    Sort.Direction direction = Sort.Direction.DESC;
    Sort sort = Sort.by(direction, "id");
    return PageRequest.of(pageChoice - 1, 4, sort);
  }

  @Override
  @Transactional
  public PostResponseDto getPost(final Long postId) {
    Post post = getPostEntity(postId);
    Long postRecommendCount = recommendService.getPostRecommendCount(post);
    List<String> options = postOpinionRepository.getOptions(post.getId());
    List<String> url = postImageRepository.getUrl(post.getId());
    return new PostResponseDto(post, postRecommendCount, url, options);
  }


  @Override
  @Transactional
  public PostResponseDto modifyPost(
      final Long postId,
      final PostRequestDto postRequestDto,
      final Account account
  ) {
    Post post = getPostEntity(postId);
    if (!post.isWriter(account)) {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
    post.modifyPost(postRequestDto);
    postRepository.save(post);
    long postRecommendCount = recommendService.getPostRecommendCount(post);
    return new PostResponseDto(post, postRecommendCount);
  }

  @Override
  @Transactional
  public void deletePost(final Long postId, final Account account) {
    Post post = getPostEntity(postId);
    if (!post.isWriter(account)) {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
    postRepository.delete(post);
  }

  @Override // 여기서 필요한 작업은 -> 해당 DB단말고 파일 데이터도 지워야 함
  public void deletePostByAdmin(final Long postId) {
    Post post = getPostEntity(postId);
    postRepository.delete(post);
  }

  @Override
  public Post getPostEntity(final Long postId) {
    return postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
  }

  @Override
  @Transactional
  public void modifyPostByAdmin(final Long postId, final PostRequestDto postRequestDto) {
    Post post = getPostEntity(postId);
    post.modifyPost(postRequestDto);
    postRepository.save(post);
  }

  @Override
  public List<TestPostResponseDto> SearchByQuerydsl(
      final PostSearchCondition condition,
      final Pageable pageable
  ) {
    List<TestPostResponseDto> testPostResponseDtos = postRepository.searchPost(condition, pageable);
    List<TestPostResponseDto> result = new ArrayList<>();
    for (int i = 0; i < testPostResponseDtos.size(); i++) {
      List<String> url = postImageRepository.getUrl(testPostResponseDtos.get(i).getId());
      result.add(new TestPostResponseDto(testPostResponseDtos.get(i), url));
    }
    return result;
  }


}
