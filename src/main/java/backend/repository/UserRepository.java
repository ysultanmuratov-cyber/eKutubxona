package backend.repository;

import backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    // Yangi qo'shilgan metod (foydalanuvchi bor-yo'qligini tekshirish uchun)
    boolean existsByUsername(String username);
}