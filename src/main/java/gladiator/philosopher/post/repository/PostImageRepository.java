package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostImageRepository extends JpaRepository<PostImage, Long>,PostCustomRepository {

  @Query("select p from PostImage  p group by p.post.id")
  List<PostImage> findAll();

  @Query("select p.imageUrl from PostImage p where p.post.id =:id")
  List<String> getUrl(@Param("id")Long id);

  void deleteByPost(Post post);

}
