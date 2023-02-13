package gladiator.philosopher.post.repository;


import gladiator.philosopher.post.dto.TestPostResponseDto;
import gladiator.philosopher.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long>{
  @Override
  Page<Post> findAll(Pageable pageable);

  @Query("select new gladiator.philosopher.post.dto.TestPostResponseDto(p.id,p.title,p.content,p.account) from Post p join p.account")
  List<TestPostResponseDto> getPost(Long id);



}
