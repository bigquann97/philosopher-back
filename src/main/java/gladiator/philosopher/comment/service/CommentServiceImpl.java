package gladiator.philosopher.comment.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.ARCHIVED_THREAD;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_AUTHORIZED_COMMENT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_COMMENT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.NOT_FOUND_OPINION;

import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentOpinionStatsDto;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.dto.FavCommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.repository.CommentRepository;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.AuthException;
import gladiator.philosopher.common.exception.InvalidAccessException;
import gladiator.philosopher.common.exception.NotFoundException;
import gladiator.philosopher.common.util.badword.BadWordFiltering;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.entity.ThreadOpinion;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final MentionService mentionService;
  private final ThreadService threadService;
  private final BadWordFiltering badWordFiltering;

  @Override
  @Transactional(readOnly = true)
  public MyPage<CommentResponseDto> selectCommentsWithPaging(
      final Long threadId,
      final int page) {
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
    String filteredContent = badWordFiltering.checkAndChange(comment.getContent());
    comment.modifyComment(filteredContent, comment.getOpinion());
    commentRepository.saveAndFlush(comment);
    mentionService.mentionComment(comment);
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
    String filteredContent = badWordFiltering.checkAndChange(dto.getContent());
    comment.modifyComment(filteredContent, dto.getOpinion());
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
    comment.chaneStatusToDeleted();
  }

  @Override
  @Transactional(readOnly = true)
  public Comment getCommentEntity(final Long id) {
    return commentRepository.findById(id)
        .orElseThrow(() -> new NotFoundException(NOT_FOUND_COMMENT));
  }

  @Override
  @Transactional(readOnly = true)
  public MyPage<CommentSimpleResponseDto> getMyComments(
      final Long accountId,
      final Pageable pageable
  ) {
    Page<CommentSimpleResponseDto> commentsByAccount = commentRepository.getCommentsByAccount(
        accountId, pageable);
    return new MyPage<>(commentsByAccount);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CommentOpinionStatsDto> selectStatistics(final Long threadId) {
    return commentRepository.selectStatistics(threadId);
  }

  @Override
  @Transactional(readOnly = true)
  public List<FavCommentResponseDto> selectFavComments(final Long threadId) {
    return commentRepository.selectFavoriteComments(threadId);
  }

  // --- Private Methods ---

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
    List<ThreadOpinion> opinions = threadService.getOpinions(thread);
    boolean threadHasOpinion = opinions.stream()
        .anyMatch(x -> x.getOpinion().equals(commentRequestDto.getOpinion()));

    if (!threadHasOpinion) {
      throw new InvalidAccessException(NOT_FOUND_OPINION);
    }
  }

}