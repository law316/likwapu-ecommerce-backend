package net.ikwa.likwapuecommerce.repository;

import net.ikwa.likwapuecommerce.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
