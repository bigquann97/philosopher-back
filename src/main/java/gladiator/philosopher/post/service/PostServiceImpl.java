package gladiator.philosopher.post.service;

import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
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
  public void createPost(List<String> urls, PostRequestDto postRequestDto,
      AccountDetails accountDetails, Category category) {
    /**
     * 포스트 저장
     */
    Post post = postRequestDto.toEntity(accountDetails.getAccount(), category);
    postRepository.save(post);

    /**
     * 포스트 옵션 저장
     */
    final List<String> opinions1 = postRequestDto.getOpinions();
    List<PostOpinion> opinions = opinions1.stream()
        .map(x -> new PostOpinion(post, x)).collect(Collectors.toList());

    postOpinionRepository.saveAll(opinions);
    /**
     * 포스트 이미지 저장
     */
    urls.forEach(url -> {
      PostImage postImage = PostImage.builder().url(url).post(post).build();
      postImageRepository.save(postImage);
    });

  }

  @Override
  @Transactional
  public List<PostsResponseDto> getPosts(int pageChoice) {
    Page<Post> posts = postRepository.findAll(pageableSetting(pageChoice));
    if (posts.isEmpty()) {
      throw new CustomException(ExceptionStatus.POST_IS_NOT_EXIST);
    }
    List<PostsResponseDto> PostResponseDtoList = posts.stream().map(PostsResponseDto::new).collect(
        Collectors.toList());
    return PostResponseDtoList;
  }

  private Pageable pageableSetting(int pageChoice) {
    Sort.Direction direction = Sort.Direction.DESC;
    Sort sort = Sort.by(direction, "id");
    Pageable pageable = PageRequest.of(pageChoice - 1, 4, sort);
    return pageable;
  }

  @Override
  @Transactional
  public PostResponseDto getPost(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    postImageRepository.findById(post.getId());
    List<String> postImage = new ArrayList<>(); // 수정해야함
    long recommendCount = recommendService.getPostRecommends(post);
    return new PostResponseDto(post, recommendCount, postImage); // 수정해야함
  }


  @Override
  @Transactional
  public PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      AccountDetails accountDetails) {
    List<String> postImage = new ArrayList<>(); // 수정해야함
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if (!post.isWriter(accountDetails)) {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
    post.modifyPost(postRequestDto);
    postRepository.save(post);
    long recommendCount = recommendService.getPostRecommends(post);
    return new PostResponseDto(post, recommendCount, postImage); // 수정해야함
  }

  @Override
  @Transactional
  public void deletePost(Long postId, AccountDetails accountDetails) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if (!post.isWriter(accountDetails)) {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
    postRepository.delete(post);
  }

  @Override // 여기서 필요한 작업은 -> 해당 DB단말고 파일 데이터도 지워야 함
  public void deletePostByAdmin(Long id) {
    Post post = postRepository.findById(id).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    postRepository.delete(post);
  }

  @Override
  public Post getPostEntity(Long postId) {
    return postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
  }

/*
postId만 필요할 경우 postId 존재 확인 후 postId를 반환
  @Override
  public Long existsPostId(Long postId) {
    if (postRepository.existsById(postId)) {
      return postId;
    }
    throw new CustomException(ExceptionStatus.POST_IS_NOT_EXIST);
  }
*/

  @Override
  @Transactional
  public void modifyPostByAdmin(Long id, PostRequestDto postRequestDto) {
    Post post = postRepository.findById(id).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    post.modifyPost(postRequestDto);
    postRepository.save(post);
  }

  @Override
  public List<TestPostResponseDto> getPostAndAccount(Long id) {
    return postRepository.getPost(id);
  }

}
