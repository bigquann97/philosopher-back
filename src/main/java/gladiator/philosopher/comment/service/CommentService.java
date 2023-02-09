package gladiator.philosopher.comment.service;

import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.security.members.MemberDetails;
import java.util.List;

public interface CommentService {

  List<CommentResponseDto> getComments(Long postId, MemberDetails memberDetails);

  CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId,
      MemberDetails memberDetails);

  CommentResponseDto modifyComment(CommentRequestDto commentRequestDto, Long commentId, Long postId,
      MemberDetails memberDetails);

  void deleteComment(Long postId, Long commentId, MemberDetails memberDetails);

}
