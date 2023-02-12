package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.security.AccountDetails;
import java.util.List;

public interface CommentService {

  List<CommentResponseDto> getComments(Long threadId);

  CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long threadId,
      AccountDetails accountDetails);

  Comment getCommentEntity(Long id);

  void modifyComment(Long commentId, String content);

  void deleteComment(Long commentId);

}
