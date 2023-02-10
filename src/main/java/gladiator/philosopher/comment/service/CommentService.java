package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;

public interface CommentService {

  List<CommentResponseDto> getComments(Long threadId);

  CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long threadId,
      MemberDetails memberDetails);

  CommentResponseDto modifyComment(CommentRequestDto commentRequestDto, Long threadId,
      Long commentId, MemberDetails memberDetails);

  void deleteComment(Long threadId, Long commentId, MemberDetails memberDetails);

  Comment getCommentEntity(Long id);
}
