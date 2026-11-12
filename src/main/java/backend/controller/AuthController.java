package backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    // Login sahifasini ochish uchun
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // login.html faylini ochadi
    }

    // Ro'yxatdan o'tish sahifasini ochish uchun
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // register.html faylini ochadi
    }

    // Ro'yxatdan o'tish formasidan ma'lumot kelganda ishlaydigan qism
    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password) {
        // Bu yerda kelajakda foydalanuvchini bazaga saqlash kodi yoziladi
        System.out.println("Yangi foydalanuvchi emaili: " + email);
        
        // Ro'yxatdan o'tib bo'lgach login sahifasiga yo'naltiramiz
        return "redirect:/login?success";
    }
}