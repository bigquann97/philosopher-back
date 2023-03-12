package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.dto.CommentOpinionStatsDto;
import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.comment.dto.FavCommentResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {

  MyPage<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long id);

  List<CommentOpinionStatsDto> selectStatistics(Long threadId);

  List<FavCommentResponseDto> selectFavoriteComments(Long threadId);

}
