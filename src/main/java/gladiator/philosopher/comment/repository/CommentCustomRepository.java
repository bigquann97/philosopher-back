package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.dto.CommentResponseDto;
import gladiator.philosopher.common.dto.MyPage;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {

  MyPage<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long id);
}
