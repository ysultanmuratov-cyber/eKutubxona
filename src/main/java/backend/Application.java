package backend;

import backend.model.User;
import backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // Dastur ishga tushganda ishlaydigan qism
    @Bean
    CommandLineRunner initSuperAdmin(UserRepository userRepository) {
        return args -> {
            String adminUsername = "superadmin";
            
            // Agar bazada bu username yo'q bo'lsa, Super Admin yaratamiz
            if (userRepository.findByUsername(adminUsername).isEmpty()) {
                User admin = new User();
                admin.setUsername(adminUsername);
                admin.setPassword("admin12345"); // Kelgusida shifrlanadi
                admin.setRole("ROLE_SUPER_ADMIN");
                
                userRepository.save(admin);
                System.out.println(">>> Super Admin muvaffaqiyatli yaratildi! Username: " + adminUsername);
            }
        };
    }
}