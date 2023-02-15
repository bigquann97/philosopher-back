package gladiator.philosopher.post.service;

import static gladiator.philosopher.post.entity.QPostImage.postImage;

import gladiator.philosopher.category.entity.Category;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.image.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final ImageService imageService;
  private final PostImageRepository postImageRepository;
  private final RecommendService recommendService;
  private final PostOpinionRepository postOpinionRepository;

  @Override
  @Transactional
  public void createPost(List<MultipartFile> multipartFiles, PostRequestDto postRequestDto,
      AccountDetails accountDetails) {
    List<PostImage> postImages = new ArrayList<>();

    Post post = Post.builder()
        .account(accountDetails.getAccount())
        .title(postRequestDto.getTitle())
        .content(postRequestDto.getContent())
        .build();

    List<PostOpinion> opinions = postRequestDto.getOpinions().stream()
        .map(x -> new PostOpinion(post, x)).collect(Collectors.toList());

    postOpinionRepository.saveAll(opinions);

    for (MultipartFile multipartFile : multipartFiles) {
      PostImage postImage = new PostImage(multipartFile.getOriginalFilename(), post);
      imageService.upload(multipartFile, postImage.getImageUrl());
      postImageRepository.save(postImage);
      postImages.add(postImage);
    }
    postRepository.save(post);
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

  @Override
  public void createPost(List<String> urls, PostRequestDto postRequestDto,
      AccountDetails accountDetails, Category category) {

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
    long postRecommendCount = recommendService.getPostRecommendCount(post);
    return new PostResponseDto(post, postRecommendCount);
  }


  @Override
  @Transactional
  public PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      AccountDetails accountDetails) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    if (!post.isWriter(accountDetails)) {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
    post.modifyPost(postRequestDto);
    postRepository.save(post);
    long postRecommendCount = recommendService.getPostRecommendCount(post);
    return new PostResponseDto(post, postRecommendCount);
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
  public List<String> getgetget(Long id) {
    final List<String> url = postImageRepository.getUrl(id);
    return url;
  }

//  List<PostImage> urlList = jpaQueryFactory
//      .selectFrom(postImage)
//      .where(postImage.post.id.in(data.stream().map(m -> m.getId()).collect(Collectors.toList())))
//      .fetch();
//
//    for (int i = 0; i < data.size(); i++) { // data size 만큼만 돌고,
//    for (PostImage url : urlList) { // 이건 url 전체 돌고
//      if (data.get(i).getId().equals(url.getPost().getId()))  // 지금 data의 0번 인덱스 -> post 1번 게시물
//      {
//        results.add(new TestPostResponseDto(data.get(i), url.getImageUrl()));
//      }
//
//    }
//  }

  @Override
  public List<TestPostResponseDto> getPosts(PostSearchCondition condition, Pageable pageable) {
    List<TestPostResponseDto> testPostResponseDtos = postRepository.searchPost(condition, pageable);
    System.out.println(testPostResponseDtos);

    List<TestPostResponseDto> result = new ArrayList<>();

    for(int i=0; i< testPostResponseDtos.size(); i++){
      List<String> url = postImageRepository.getUrl(testPostResponseDtos.get(i).getId());
      result.add(new TestPostResponseDto(testPostResponseDtos.get(i),url));
    }

    return result;
  }


}
