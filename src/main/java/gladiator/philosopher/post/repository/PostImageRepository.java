package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface PostImageRepository extends JpaRepository<PostImage, Long>, PostCustomRepository {

  List<PostImage> findByPost(@NonNull Post post);

  @Query("select p from PostImage  p group by p.post.id")
  List<PostImage> findAll();

  @Query("select p.imageUrl from PostImage p where p.post.id =:id")
  List<String> getUrl(@Param("id") Long id);

  @Modifying
  @Transactional
  @Query("delete from PostImage p where p.post= ?1")
  void deleteAllByPostImage(Post post);

}
