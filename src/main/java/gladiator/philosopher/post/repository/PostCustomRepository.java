package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {

  Page<PostsResponseDto> searchPost(PostSearchCondition condition, Pageable pageable);

}
