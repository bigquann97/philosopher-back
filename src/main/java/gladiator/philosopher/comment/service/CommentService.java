package gladiator.philosopher.comment.service;

import gladiator.philosopher.account.dto.AccountCommentResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;

public interface CommentService {

  MyPage<CommentResponseDto> selectCommentsWithPaging(final Long threadId, int page);

  void createComment(
      final CommentRequestDto commentRequestDto,
      final Thread threadId,
      final Account account
  );

  void modifyComment(CommentRequestDto commentRequestDto, Long commentId, Account account);

  void deleteComment(Long commentId, Account account);

  Comment getCommentEntity(Long id);

  void modifyCommentByAdmin(Long id, CommentRequestDto commentRequestDto);

  void deleteCommentByAdmin(Long id);

  List<AccountCommentResponseDto> findMyComments(Account account, int pageNum);
}
