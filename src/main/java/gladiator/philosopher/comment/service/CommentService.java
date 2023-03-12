package gladiator.philosopher.comment.service;

import gladiator.philosopher.account.dto.CommentSimpleResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.dto.CommentOpinionStatsDto;
import gladiator.philosopher.comment.dto.CommentRequestDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.dto.FavCommentResponseDto;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentService {

  MyPage<CommentResponseDto> selectCommentsWithPaging(
      final Long threadId,
      final int page
  );

  void createComment(
      final CommentRequestDto commentRequestDto,
      final Thread threadId,
      final Account account
  );

  void modifyComment(
      final CommentRequestDto commentRequestDto,
      final Long commentId,
      final Account account
  );

  void deleteComment(
      final Long commentId,
      final Account account);

  Comment getCommentEntity(final Long id);

  List<CommentOpinionStatsDto> selectStatistics(final Long threadId);

  List<FavCommentResponseDto> selectFavComments(final Long threadId);

  MyPage<CommentSimpleResponseDto> getMyComments(
      final Long accountId,
      final Pageable pageable);

}
