package gladiator.philosopher.comment.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.enums.ExceptionStatus;
import gladiator.philosopher.common.exception.CustomException;
import gladiator.philosopher.mention.service.MentionService;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
  public Page<CommentResponseDto> selectCommentsWithPaging(final Long threadId, int page) {
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
    comment.modifyComment(dto.getContent());
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
        .orElseThrow(() -> new IllegalArgumentException("댓글 없음"));
  }

  @Override
  @Transactional
  public void modifyCommentByAdmin(final Long id, final CommentRequestDto commentRequestDto) {
    Comment comment = getCommentEntity(id);
    comment.modifyComment(comment.getContent());
  }

  @Override
  @Transactional
  public void deleteCommentByAdmin(final Long id) {
    Comment comment = getCommentEntity(id);
    commentRepository.delete(comment);
  }

  private void checkIfAccountIsWriter(final Comment comment, final Account account) {
    if (!comment.isWriter(account)) {
      throw new CustomException(ExceptionStatus.UNMATCHED_USER);
    }
  }

  private void checkIfThreadIsArchived(Thread thread) {
    if (thread.isArchived()) {
      throw new IllegalArgumentException("토론 종료된 쓰레드 입니다.");
    }
  }

  private void checkIfThreadHasOpinion(
      final Thread thread,
      final CommentRequestDto commentRequestDto
  ) {
    boolean threadHasOpinion = thread.getOpinions().stream()
        .anyMatch(x -> x.getOpinion().equals(commentRequestDto.getOpinion()));

    if (!threadHasOpinion) {
      throw new IllegalArgumentException("논제에 없는 의견입니다.");
    }
  }

}