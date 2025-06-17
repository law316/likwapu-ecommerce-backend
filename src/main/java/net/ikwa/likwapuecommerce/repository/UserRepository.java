package net.ikwa.likwapuecommerce.repository;

import net.ikwa.likwapuecommerce.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer>{
    Optional<Object> findByEmail(String email);
}
