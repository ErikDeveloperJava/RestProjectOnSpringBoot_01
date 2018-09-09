package restfull.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restfull.demo.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
