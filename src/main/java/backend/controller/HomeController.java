package backend.controller;

import backend.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    // DIQQAT: /register bu yerda o'chirildi, chunki u AuthController da joylashgan!

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/catalog")
    public String catalogPage() {
        return "catalog";
    }

    @GetMapping("/audio-books")
    public String audioBooksPage() {
        return "audio-books";
    }

    @GetMapping("/favorites")
    public String favoritesPage() {
        return "favorites";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(name = "query", required = false, defaultValue = "") String query, Model model) {
        List<Book> allBooks = new ArrayList<>();
        allBooks.add(new Book("O'tkan kunlar", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/415071/pexels-photo-415071.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allBooks.add(new Book("Mehrobdan Chayon", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/1029141/pexels-photo-1029141.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allBooks.add(new Book("Kecha va Kunduz", "Cho'lpon", "Tarixiy", "https://images.pexels.com/photos/2047905/pexels-photo-2047905.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allBooks.add(new Book("Sariq Devni Minib", "Xudoyberdi To'xtaboyev", "Bolalar", "https://images.pexels.com/photos/1907785/pexels-photo-1907785.jpeg?auto=compress&cs=tinysrgb&w=600"));

        List<Book> filteredBooks = allBooks.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                             b.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                             b.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("searchQuery", query);
        model.addAttribute("books", filteredBooks);
        return "search-results";
    }

    @GetMapping("/book/{title}")
    public String bookDetail(@org.springframework.web.bind.annotation.PathVariable("title") String title, Model model) {
        List<Book> allBooks = new ArrayList<>();
        allBooks.add(new Book("O'tkan kunlar", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/415071/pexels-photo-415071.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allBooks.add(new Book("Mehrobdan Chayon", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/1029141/pexels-photo-1029141.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allBooks.add(new Book("Kecha va Kunduz", "Cho'lpon", "Tarixiy", "https://images.pexels.com/photos/2047905/pexels-photo-2047905.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allBooks.add(new Book("Sariq Devni Minib", "Xudoyberdi To'xtaboyev", "Bolalar", "https://images.pexels.com/photos/1907785/pexels-photo-1907785.jpeg?auto=compress&cs=tinysrgb&w=600"));

        Book foundBook = allBooks.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(allBooks.get(0));

        model.addAttribute("book", foundBook);
        return "book-detail";
    }

    @GetMapping("/ebooks")
    public String ebooksPage(Model model) {
        List<Book> ebooks = new ArrayList<>();
        ebooks.add(new Book("O'tkan kunlar", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/415071/pexels-photo-415071.jpeg?auto=compress&cs=tinysrgb&w=600"));
        ebooks.add(new Book("Mehrobdan Chayon", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/1029141/pexels-photo-1029141.jpeg?auto=compress&cs=tinysrgb&w=600"));
        ebooks.add(new Book("Kecha va Kunduz", "Cho'lpon", "Tarixiy", "https://images.pexels.com/photos/2047905/pexels-photo-2047905.jpeg?auto=compress&cs=tinysrgb&w=600"));
        ebooks.add(new Book("Sariq Devni Minib", "Xudoyberdi To'xtaboyev", "Bolalar", "https://images.pexels.com/photos/1907785/pexels-photo-1907785.jpeg?auto=compress&cs=tinysrgb&w=600"));

        model.addAttribute("books", ebooks);
        return "ebooks";
    }

    @GetMapping("/ebooks/search")
    public String searchEbooks(@RequestParam(name = "query", required = false, defaultValue = "") String query, Model model) {
        List<Book> allEbooks = new ArrayList<>();
        allEbooks.add(new Book("O'tkan kunlar", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/415071/pexels-photo-415071.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allEbooks.add(new Book("Mehrobdan Chayon", "Abdulla Qodiriy", "Badiiy", "https://images.pexels.com/photos/1029141/pexels-photo-1029141.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allEbooks.add(new Book("Kecha va Kunduz", "Cho'lpon", "Tarixiy", "https://images.pexels.com/photos/2047905/pexels-photo-2047905.jpeg?auto=compress&cs=tinysrgb&w=600"));
        allEbooks.add(new Book("Sariq Devni Minib", "Xudoyberdi To'xtaboyev", "Bolalar", "https://images.pexels.com/photos/1907785/pexels-photo-1907785.jpeg?auto=compress&cs=tinysrgb&w=600"));

        List<Book> filteredBooks = allEbooks.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                             b.getAuthor().toLowerCase().contains(query.toLowerCase()) ||
                             b.getCategory().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("searchQuery", query);
        model.addAttribute("books", filteredBooks);
        return "ebooks";
    }
}