package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<Post, Long> {

}
