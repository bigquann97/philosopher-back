package gladiator.philosopher.comment.repository;

import gladiator.philosopher.comment.dto.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {

  Page<CommentResponseDto> selectCommentsWithPaging(Pageable pageable, Long id);
}
