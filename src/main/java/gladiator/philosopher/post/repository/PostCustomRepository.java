package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {

  List<TestPostResponseDto> searchPost(PostSearchCondition condition, Pageable pageable);

}
