package restfull.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restfull.demo.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByCategories_Id(int id);
}
