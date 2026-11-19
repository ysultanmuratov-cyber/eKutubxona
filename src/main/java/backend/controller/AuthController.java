package backend.controller;

import backend.model.User;
import backend.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Bunday login allaqachon mavjud! Boshqa login kiriting.");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setRole("ROLE_USER");
        
        userRepository.save(newUser);
        session.setAttribute("loggedInUser", newUser.getUsername());
        session.setAttribute("userRole", newUser.getRole());
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    session.setAttribute("loggedInUser", user.getUsername());
                    session.setAttribute("userRole", user.getRole());

                    String role = user.getRole();

                    if (role != null && !role.trim().equalsIgnoreCase("ROLE_USER")) {
                        return "redirect:/admin/dashboard";
                    }

                    return "redirect:/home";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Login yoki parol noto'g'ri!");
                    return "login";
                });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}