package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.security.AccountDetails;
import java.util.List;

public interface CommentService {

  List<CommentResponseDto> getComments(Long postId, Long threadId);

  CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId, Long threadId,
      AccountDetails accountDetails);

  CommentResponseDto modifyComment(CommentRequestDto commentRequestDto, Long postId, Long threadId,
      Long commentId, AccountDetails accountDetails);

  void deleteComment(Long postId, Long threadId, Long commentId, AccountDetails accountDetails);

  Comment getCommentEntity(Long id);
}
