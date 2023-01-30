package shah.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shah.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
