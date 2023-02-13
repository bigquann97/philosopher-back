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

  void modifyComment(Long commentId, String content, Long id);

  void deleteComment(Long commentId, Long id);

  Comment getCommentEntity(Long id);

  void modifyCommentByAdmin(Long id, CommentRequestDto commentRequestDto);

  void deleteCommentByAdmin(Long id);

}
