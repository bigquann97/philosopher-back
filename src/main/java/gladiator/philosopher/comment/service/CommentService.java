package gladiator.philosopher.comment.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;

public interface CommentService {

  List<CommentResponseDto> getComments(final Thread threadId);

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
