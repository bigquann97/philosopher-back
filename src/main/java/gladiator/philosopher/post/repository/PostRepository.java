package gladiator.philosopher.post.repository;


import gladiator.philosopher.account.dto.PostSimpleResponseDto;
import gladiator.philosopher.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

  @Override
  Page<Post> findAll(Pageable pageable);

  @Query("select new gladiator.philosopher.account.dto.PostSimpleResponseDto(p.id,p.title, p.content, p.category.name) from Post p where p.account.id=:id order by p.id DESC ")
  Page<PostSimpleResponseDto> getPostsByAccount(@Param("id")Long accountId, Pageable pageable);

}
