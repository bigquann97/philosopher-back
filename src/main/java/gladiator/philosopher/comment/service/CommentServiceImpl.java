package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.common.security.AccountDetails;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.repository.PostRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.repository.ThreadRepository;
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

  private final ThreadRepository threadRepository;

  @Override
  public List<CommentResponseDto> getComments(Long threadId) {
    Thread thread = threadRepository.findById(threadId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    List<Comment> comments = commentRepository.findAllByThread(thread);
    return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long threadId,
      AccountDetails accountDetails) {
    Thread thread = threadRepository.findById(threadId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    Comment comment = Comment.builder()
        .account(accountDetails.getAccount())
        .thread(thread)
        .content(commentRequestDto.getContent())
        .build();

    comment = commentRepository.save(comment);
    return new CommentResponseDto(comment);
  }

  @Override
  @Transactional
  public CommentResponseDto modifyComment(CommentRequestDto commentRequestDto, Long threadId,
      Long commentId, AccountDetails accountDetails) {
    Thread thread = threadRepository.findById(threadId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CustomException(ExceptionStatus.POST_IS_NOT_EXIST));

    Comment modifyComment = Comment.builder()
        .id(comment.getId())
        .account(accountDetails.getAccount())
        .thread(thread)
        .content(commentRequestDto.getContent())
        .build();

    comment = commentRepository.save(modifyComment);
    return new CommentResponseDto(comment);
  }

  @Override
  @Transactional
  public void deleteComment(Long threadId, Long commentId, AccountDetails accountDetails) {
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