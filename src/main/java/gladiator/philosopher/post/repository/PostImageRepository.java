package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long>,PostCustomRepository {

}
