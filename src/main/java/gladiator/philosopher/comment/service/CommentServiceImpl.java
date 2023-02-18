package gladiator.philosopher.comment.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.ARCHIVED_THREAD;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_AUTHORIZED_COMMENT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_COMMENT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_OPINION;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.AuthException;
import gladiator.philosopher.common.exception.InvalidAccessException;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final MentionService mentionService;
  private final ThreadService threadService;

  @Override
  @Transactional(readOnly = true)
  public MyPage<CommentResponseDto> selectCommentsWithPaging(final Long threadId, int page) {
    PageRequest pageable = PageRequest.of(page, 10);
    return commentRepository.selectCommentsWithPaging(pageable, threadId);
  }

  @Override
  @Transactional
  public void createComment(
      final CommentRequestDto commentRequestDto,
      final Thread thread,
      final Account account
  ) {
    Thread foundThread = threadService.getThreadEntity(thread.getId());
    checkIfThreadIsArchived(foundThread);
    checkIfThreadHasOpinion(foundThread, commentRequestDto);
    Comment comment = commentRequestDto.toEntity(foundThread, account);
    mentionService.mentionComment(comment);
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void modifyComment(
      final CommentRequestDto dto,
      final Long commentId,
      final Account account
  ) {
    Comment comment = getCommentEntity(commentId);
    checkIfAccountIsWriter(comment, account);
    mentionService.deleteMentions(comment);
    comment.modifyComment(dto.getContent(), dto.getOpinion());
    mentionService.mentionComment(comment);
    commentRepository.save(comment);
  }

  @Override
  @Transactional
  public void deleteComment(
      final Long commentId,
      final Account account
  ) {
    Comment comment = getCommentEntity(commentId);
    checkIfAccountIsWriter(comment, account);
    mentionService.deleteMentions(comment);
    commentRepository.delete(comment);
  }

  @Override
  @Transactional(readOnly = true)
  public Comment getCommentEntity(final Long id) {
    return commentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_COMMENT));
  }

  @Override
  @Transactional
  public void modifyCommentByAdmin(final Long id, final CommentRequestDto commentRequestDto) {
    Comment comment = getCommentEntity(id);
    comment.modifyComment(comment.getContent(), comment.getOpinion());
  }

  @Override
  @Transactional
  public void deleteCommentByAdmin(final Long id) {
    Comment comment = getCommentEntity(id);
    commentRepository.delete(comment);
  }

  private void checkIfAccountIsWriter(final Comment comment, final Account account) {
    if (!comment.isWriter(account)) {
      throw new AuthException(NOT_AUTHORIZED_COMMENT);
    }
  }

  private void checkIfThreadIsArchived(Thread thread) {
    if (thread.isArchived()) {
      throw new InvalidAccessException(ARCHIVED_THREAD);
    }
  }

  private void checkIfThreadHasOpinion(
      final Thread thread,
      final CommentRequestDto commentRequestDto
  ) {
    boolean threadHasOpinion = thread.getOpinions().stream()
        .anyMatch(x -> x.getOpinion().equals(commentRequestDto.getOpinion()));

    if (!threadHasOpinion) {
      throw new InvalidAccessException(NOT_FOUND_OPINION);
    }
  }

}