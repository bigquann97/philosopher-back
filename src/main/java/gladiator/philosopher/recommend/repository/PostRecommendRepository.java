package gladiator.philosopher.recommend.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.recommend.entity.PostRecommend;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface PostRecommendRepository extends JpaRepository<PostRecommend, Long> {

  long countByPost(@NonNull Post post);

  boolean existsByPostAndAccount(Post post, Account account);

  Optional<PostRecommend> findByPostAndAccount(Post post, Account account);

  List<PostRecommend> findByPost(@NonNull Post post);

}
