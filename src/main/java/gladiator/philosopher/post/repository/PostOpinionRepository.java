package gladiator.philosopher.post.repository;

import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.entity.PostOpinion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface PostOpinionRepository extends JpaRepository<PostOpinion, Long> {

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("delete from PostOpinion p where p.post.id =:id")
  void deleteAllByPostOpinion(@Param("id") Long id);

  List<PostOpinion> findByPost(@NonNull Post post);

  @Query("select p.opinion from PostOpinion p where p.post.id=:id")
  List<String> getOptions(@Param("id") Long id);

}
