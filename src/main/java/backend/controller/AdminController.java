package backend.controller;

import backend.model.User;
import backend.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    // Yordamchi metod: Foydalanuvchida qandaydir admin/menejer huquqi borligini tekshirish
    private boolean hasAdminAccess(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null) return false;
        // ROLE_USER bo'lmasa, demak u har qanday turdagi admindir
        return !role.trim().equalsIgnoreCase("ROLE_USER");
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!hasAdminAccess(session)) {
            return "redirect:/login";
        }

        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin_dashboard";
    }

    // --- Yangi admin qo'shish sahifasini ochish ---
    @GetMapping("/add-admin")
    public String showAddAdminPage(HttpSession session, Model model) {
        // Yangi admin qo'shish sahifasini FAQAT Super Admin ko'ra olsin desangiz:
        String role = (String) session.getAttribute("userRole");
        if (role == null || !"ROLE_SUPER_ADMIN".equals(role)) {
            return "redirect:/admin/dashboard"; // yoki login
        }
        return "add_admin";
    }

    // --- Yangi adminni bazaga saqlash ---
    @PostMapping("/add-admin")
    public String createAdmin(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam(value = "permissions", required = false) List<String> permissions,
                              HttpSession session,
                              Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !"ROLE_SUPER_ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }

        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Bu username allaqachon mavjud!");
            return "add_admin";
        }

        User newAdmin = new User();
        newAdmin.setUsername(username);
        newAdmin.setPassword(password);

        // Toggle switch orqali yoqilgan huquqlarni vergul bilan birlashtirib saqlaymiz
        if (permissions != null && !permissions.isEmpty()) {
            newAdmin.setRole(String.join(",", permissions));
        } else {
            newAdmin.setRole("CONTENT_MANAGER");
        }

        userRepository.save(newAdmin);
        return "redirect:/admin/dashboard";
    }
    // --- Admin huquqlarini o'zgartirish sahifasini ochish ---
    @GetMapping("/edit-admin/{id}")
    public String showEditAdminPage(@PathVariable("id") Long id, HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !"ROLE_SUPER_ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }

        User targetUser = userRepository.findById(id).orElse(null);
        if (targetUser == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("targetUser", targetUser);
        return "edit_admin";
    }

    // --- Admin huquqlarini bazada yangilash ---
    @PostMapping("/edit-admin/{id}")
    public String updateAdminPermissions(@PathVariable("id") Long id,
                                         @RequestParam(value = "permissions", required = false) List<String> permissions,
                                         HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !"ROLE_SUPER_ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }

        User targetUser = userRepository.findById(id).orElse(null);
        if (targetUser != null) {
            if (permissions != null && !permissions.isEmpty()) {
                targetUser.setRole(String.join(",", permissions));
            } else {
                targetUser.setRole("ROLE_USER"); // Agar barcha huquqlar o'chirilsa, oddiy foydalanuvchiga aylanadi
            }
            userRepository.save(targetUser);
        }

        return "redirect:/admin/dashboard";
    }

    // --- Adminni oddiy foydalanuvchiga tushirish (yoki adminlikdan mahrum etish) ---
    @GetMapping("/remove-admin/{id}")
    public String removeAdmin(@PathVariable("id") Long id, HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !"ROLE_SUPER_ADMIN".equals(role)) {
            return "redirect:/admin/dashboard";
        }

        User targetUser = userRepository.findById(id).orElse(null);
        if (targetUser != null) {
            targetUser.setRole("ROLE_USER"); // Rolini oddiy userga o'zgartiramiz
            userRepository.save(targetUser);
        }

        return "redirect:/admin/dashboard";
    }
}