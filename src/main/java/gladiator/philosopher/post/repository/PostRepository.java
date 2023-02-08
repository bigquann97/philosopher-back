package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findById(Long postId);
}
