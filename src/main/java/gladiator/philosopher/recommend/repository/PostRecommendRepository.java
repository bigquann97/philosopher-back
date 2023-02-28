package gladiator.philosopher.recommend.repository;

import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.PostRecommend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {

  long countByPost(@NonNull Post post);

  boolean existsByPostAndAccount(Post post, Account account);

  Optional<PostRecommend> findByPostAndAccount(Post post, Account account);

  List<PostRecommend> findByPost(@NonNull Post post);

  Page<PostRecommend> findPostRecommendByAccount(Account account, Pageable pageable);

  @Query("select new gladiator.philosopher.account.dto.PostSimpleResponseDto(pr.id,pr.post.title, pr.post.content, pr.post.category.name) from PostRecommend pr where pr.account=:account order by pr.id DESC ")
  Page<PostSimpleResponseDto> getAllPosts(@Param("account") Account account, Pageable pageable);
}
