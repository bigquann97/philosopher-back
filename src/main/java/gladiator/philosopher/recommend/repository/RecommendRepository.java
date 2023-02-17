package gladiator.philosopher.recommend.repository;


import gladiator.philosopher.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {

}
