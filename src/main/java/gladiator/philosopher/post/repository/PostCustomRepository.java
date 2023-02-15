package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.TestPostResponseDto;
import java.util.List;

public interface PostCustomRepository {

  List<TestPostResponseDto> searchPost(PostSearchCondition condition);

}
