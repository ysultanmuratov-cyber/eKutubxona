package backend.controller;

import backend.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String query,
                       HttpSession session,
                       Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", username);

        if (query != null && !query.trim().isEmpty()) {
            model.addAttribute("books", bookRepository
                    .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(
                            query, query, query));
            model.addAttribute("searchQuery", query);
        } else {
            model.addAttribute("books", bookRepository.findAll());
        }
        return "home";
    }

    @GetMapping("/catalog")
    public String catalog(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";
        model.addAttribute("currentUser", username);
        model.addAttribute("books", bookRepository.findAll());
        return "catalog";
    }

    @GetMapping("/ebooks")
    public String ebooks(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";
        model.addAttribute("currentUser", username);
        model.addAttribute("books", bookRepository.findByCategory("ebook"));
        return "ebooks";
    }

    @GetMapping("/audio-books")
    public String audioBooks(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";
        model.addAttribute("currentUser", username);
        model.addAttribute("books", bookRepository.findByCategory("audio"));
        return "audio-books";
    }

    @GetMapping("/favorites")
    public String favorites(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) return "redirect:/login";
        model.addAttribute("currentUser", username);
        return "favorites";
    }
}