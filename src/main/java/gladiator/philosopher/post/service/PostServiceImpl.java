package gladiator.philosopher.post.service;

import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.post.dto.PostRequestDto;
import gladiator.philosopher.post.dto.PostResponseDto;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.security.members.MemberDetails;
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

  @Override
  @Transactional
  public PostResponseDto newPost(PostRequestDto postRequestDto, MemberDetails memberDetails) {
    Post post = Post.builder()
        .account(memberDetails.getMember())
        .title(postRequestDto.getTitle())
        .content(postRequestDto.getContent())
        .build();
    postRepository.save(post);
    return new PostResponseDto(post);
  }

  @Override
  @Transactional
  public List<PostResponseDto> getPosts(int pageChoice) {
    Page<Post> posts = postRepository.findAll(pageableSetting(pageChoice));
    if (posts.isEmpty()) {
      throw new CustomException(ExceptionStatus.POST_IS_NOT_EXIST);
    }
    List<PostResponseDto> PostResponseDtoList = posts.stream().map(PostResponseDto::new).collect(
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
    return new PostResponseDto(post);
  }

  @Override
  @Transactional
  public PostResponseDto modifyPost(Long postId, PostRequestDto postRequestDto,
      MemberDetails memberDetails) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    post.modifyPost(postRequestDto);
    postRepository.save(post);
    return new PostResponseDto(post);
  }

  @Override
  @Transactional
  public void deletePost(Long postId, MemberDetails memberDetails) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST)
    );
    postRepository.delete(post);
  }
}
