package backend.controller;

import backend.model.EBook;
import backend.repository.BookRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Fayllar saqlanadigan papka
    public static String UPLOAD_DIRECTORY = "uploads/";

    private boolean hasContentAccess(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null) return false;
        return role.equals("ROLE_SUPER_ADMIN") || role.contains("CONTENT_MANAGER");
    }

    @GetMapping("/admin/content")
    public String contentManagerDashboard(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) String category,
            HttpSession session,
            Model model) {
        if (!hasContentAccess(session)) return "redirect:/login";

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasCategory = category != null && !category.trim().isEmpty();

        List<EBook> books;

        if (hasKeyword && hasCategory) {
            String kw = keyword.trim();
            String cat = category.trim();

            List<EBook> byTitle = bookRepository.findByTitleContainingIgnoreCaseAndCategory(kw, cat);
            List<EBook> byAuthor = bookRepository.findByAuthorContainingIgnoreCaseAndCategory(kw, cat);

            books = new ArrayList<>(byTitle);
            for (EBook b : byAuthor) {
                if (books.stream().noneMatch(existing -> existing.getId().equals(b.getId()))) {
                    books.add(b);
                }
            }
        } else if (hasKeyword) {
            String kw = keyword.trim();
            books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(kw, kw, kw);
        } else if (hasCategory) {
            books = bookRepository.findByCategory(category.trim());
        } else {
            books = bookRepository.findAll();
        }

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedCategory", category);
        return "content_manager_dashboard";
    }

    @GetMapping("/admin/books/add")
    public String showAddBookPage(HttpSession session) {
        if (!hasContentAccess(session)) return "redirect:/login";
        return "add_book";
    }

    // --- Yangi kitobni saqlash ---
    @PostMapping("/admin/books/add")
    public String addBook(@RequestParam("title") String title,
                          @RequestParam("author") String author,
                          @RequestParam(value = "genre", required = false) String genre,
                          @RequestParam(value = "category", required = false) String category,
                          @RequestParam(value = "bookType", required = false) String bookType,
                          @RequestParam(value = "imageUrl", required = false) String imageUrl,
                          @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                          @RequestParam(value = "bookFile", required = false) MultipartFile bookFile,
                          @RequestParam(value = "description", required = false) String description,
                          HttpSession session) throws IOException {
        if (!hasContentAccess(session)) return "redirect:/login";

        EBook newBook = new EBook();
        newBook.setTitle(title);
        newBook.setAuthor(author);
        newBook.setGenre(genre);
        newBook.setCategory(category);
        newBook.setBookType(bookType);
        newBook.setDescription(description);

        // 1. Rasmni saqlash
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, imageFile.getBytes());
            newBook.setImageUrl("/uploads/" + fileName);
        } else if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            newBook.setImageUrl(imageUrl.trim());
        }

        // 2. Kitob faylini saqlash
        if (bookFile != null && !bookFile.isEmpty()) {
            String bookFileName = UUID.randomUUID().toString() + "_" + bookFile.getOriginalFilename();
            Path bookPath = Paths.get(UPLOAD_DIRECTORY + bookFileName);
            Files.createDirectories(bookPath.getParent());
            Files.write(bookPath, bookFile.getBytes());
            newBook.setContent("/uploads/" + bookFileName);
        }

        bookRepository.save(newBook);
        return "redirect:/admin/content";
    }

    @GetMapping("/admin/books/edit/{id}")
    public String showEditBookPage(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (!hasContentAccess(session)) return "redirect:/login";
        EBook book = bookRepository.findById(id).orElse(null);
        if (book == null) return "redirect:/admin/content";
        model.addAttribute("book", book);
        return "edit_book";
    }

    // --- Kitobni tahrirlash va saqlash ---
    @PostMapping("/admin/books/edit/{id}")
    public String updateBook(@PathVariable("id") Long id,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam(value = "genre", required = false) String genre,
                             @RequestParam(value = "category", required = false) String category,
                             @RequestParam(value = "bookType", required = false) String bookType,
                             @RequestParam(value = "imageUrl", required = false) String imageUrl,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             @RequestParam(value = "bookFile", required = false) MultipartFile bookFile,
                             @RequestParam(value = "description", required = false) String description,
                             HttpSession session) throws IOException {
        if (!hasContentAccess(session)) return "redirect:/login";

        EBook book = bookRepository.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setCategory(category);
            book.setBookType(bookType);
            book.setDescription(description);

            // Rasmni yangilash (agar yangisi yuklansa)
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIRECTORY + fileName);
                Files.createDirectories(path.getParent());
                Files.write(path, imageFile.getBytes());
                book.setImageUrl("/uploads/" + fileName);
            } else if (imageUrl != null && !imageUrl.trim().isEmpty()) {
                book.setImageUrl(imageUrl.trim());
            }

            // Kitob faylini yangilash (agar yangisi yuklansa)
            if (bookFile != null && !bookFile.isEmpty()) {
                String bookFileName = UUID.randomUUID().toString() + "_" + bookFile.getOriginalFilename();
                Path bookPath = Paths.get(UPLOAD_DIRECTORY + bookFileName);
                Files.createDirectories(bookPath.getParent());
                Files.write(bookPath, bookFile.getBytes());
                book.setContent("/uploads/" + bookFileName);
            }

            bookRepository.save(book);
        }
        return "redirect:/admin/content";
    }

    @GetMapping("/admin/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, HttpSession session) {
        if (!hasContentAccess(session)) return "redirect:/login";
        bookRepository.deleteById(id);
        return "redirect:/admin/content";
    }
}