package gladiator.philosopher.comment.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import org.springframework.data.domain.Page;

public interface CommentService {

  Page<CommentResponseDto> selectCommentsWithPaging(final Thread threadId, int page);

  void createComment(
      final CommentRequestDto commentRequestDto,
      final Thread threadId,
      final Account account
  );

  void modifyComment(CommentRequestDto commentRequestDto, Long commentId, Account account);

  void deleteComment(CommentRequestDto commentRequestDto, Long commentId, Account account);

  Comment getCommentEntity(Long id);

  void modifyCommentByAdmin(Long id, CommentRequestDto commentRequestDto);

  void deleteCommentByAdmin(Long id);

}
