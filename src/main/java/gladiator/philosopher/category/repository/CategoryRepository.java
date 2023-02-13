package gladiator.philosopher.category.repository;

import gladiator.philosopher.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
