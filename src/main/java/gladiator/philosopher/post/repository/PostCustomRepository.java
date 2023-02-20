package gladiator.philosopher.post.repository;

import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.post.dto.PostSearchCondition;
import gladiator.philosopher.post.dto.PostResponseDtoByQueryDsl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {

  MyPage<PostResponseDtoByQueryDsl> searchPost(PostSearchCondition condition, Pageable pageable);

}
