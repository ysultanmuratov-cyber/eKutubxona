package backend.controller;

import backend.model.EBook;
import backend.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/book/{title}")
    public String viewBook(@PathVariable String title, HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", username);

        // Kitob nomiga qarab bazadan qidiramiz
        EBook book = bookRepository.findAll().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (book == null) {
            return "redirect:/home";
        }

        model.addAttribute("book", book);
        return "book-detail"; // Yoki kitob o'qish sahifangizning nomi (masalan, book.html)
    }
    @GetMapping("/read/{title}")
    public String readBook(@PathVariable String title, HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUser");
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("currentUser", username);

        EBook book = bookRepository.findAll().stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);

        if (book == null) {
            return "redirect:/home";
        }

        model.addAttribute("book", book);
        return "read-book"; // read-book.html sahifasini ochadi
    }
}
