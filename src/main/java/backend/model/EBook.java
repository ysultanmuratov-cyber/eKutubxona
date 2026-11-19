package backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class EBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private String category;
    private String bookType; // "Elektron kitob" yoki "Audiokitob"
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "LONGTEXT")
    private String content; // Kitob faylining yo'li yoki matni

    // Bo'sh konstruktor
    public EBook() {
    }

    // 1. Eskicha 7 ta parametrli konstruktor (boshlang'ich kitoblar uchun)
    public EBook(String title, String author, String genre, String category, String imageUrl, String description, String content) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.category = category;
        this.imageUrl = imageUrl;
        this.description = description;
        this.content = content;
    }

    // 2. Yangi 8 ta parametrli konstruktor (bookType bilan birga)
    public EBook(String title, String author, String genre, String category, String bookType, String imageUrl, String description, String content) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.category = category;
        this.bookType = bookType;
        this.imageUrl = imageUrl;
        this.description = description;
        this.content = content;
    }

    // Getter va Setter metodlari
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getBookType() { return bookType; }
    public void setBookType(String bookType) { this.bookType = bookType; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}