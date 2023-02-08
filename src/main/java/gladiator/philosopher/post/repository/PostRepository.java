package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Override
  Page<Post> findAll(Pageable pageable);
}
