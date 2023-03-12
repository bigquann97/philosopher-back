package gladiator.philosopher.category.repository;

import gladiator.philosopher.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  long deleteByName(@NonNull String name);

}
