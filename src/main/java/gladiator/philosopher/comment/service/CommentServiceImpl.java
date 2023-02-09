package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.exception.ExceptionStatus;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;

  private final PostRepository postRepository;

  @Override
  public List<CommentResponseDto> getComments(Long postId, MemberDetails memberDetails) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    List<Comment> comments = commentRepository.findAllByPost(post);
    return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId,
      MemberDetails memberDetails) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    Comment comment = Comment.builder()
        .account(memberDetails.getMember())
        .post(post)
        .content(commentRequestDto.getContent())
        .build();

    comment = commentRepository.save(comment);
    return new CommentResponseDto(comment);
  }

  @Override
  @Transactional
  public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto, Long commentId,
      Long postId, MemberDetails memberDetails) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    Comment modifyComment = Comment.builder()
        .id(comment.getId())
        .account(memberDetails.getMember())
        .post(post)
        .content(commentRequestDto.getContent())
        .build();

    comment = commentRepository.save(modifyComment);
    return new CommentResponseDto(comment);
  }

  @Override
  @Transactional
  public void deleteComment(Long postId, Long commentId, MemberDetails memberDetails) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    commentRepository.delete(comment);
  }

  @Override
  @Transactional
  public Comment getCommentEntity(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));
    return comment;
  }
}