package restfull.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restfull.demo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String name);
}