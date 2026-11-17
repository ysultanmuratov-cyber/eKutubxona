package backend.config;

import backend.model.EBook;
import backend.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initBooks(BookRepository bookRepository) {
        return args -> {
            // Agar jadval bo'sh bo'lsa yoki eski ma'lumotlarni yangilash kerak bo'lsa:
            if (bookRepository.count() == 0) {
                List<EBook> defaultBooks = List.of(
                    new EBook(
                        "O'tkan kunlar", 
                        "Abdulla Qodiriy", 
                        "Tarixiy roman", 
                        "ebook",
                        "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=500",
                        "O'zbek romanchiligining ilk va eng buyuk durdonasi.",
                        "1-bob\n\nKuz kunlarining birida Toshkent vaqfi cho'lida..."
                    ),
                    new EBook(
                        "Mehrobdan chayon", 
                        "Abdulla Qodiriy", 
                        "Tarixiy roman", 
                        "ebook",
                        "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500",
                        "Anvar va Ra'noning sadoqatli muhabbati haqida asar.",
                        "1-bob\n\nMirzakarim qutidorning hovlisida..."
                    ),
                    new EBook(
                        "Sariq devni minib", 
                        "Xudoyberdi To'xtaboyev", 
                        "Sarguzasht", 
                        "audio",
                        "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=500",
                        "Hoshimjonning sehrli qalpoqcha bilan boshidan kechirgan sarguzashtlari.",
                        "1-bob\n\nMen qanday qilib qorovul bo'lib qoldim..."
                    ),
                    new EBook(
                        "Yulduzli tunlar", 
                        "Pirimqul Qodirov", 
                        "Tarixiy roman", 
                        "ebook",
                        "https://images.unsplash.com/photo-1495446815901-a7297e633e8d?w=500",
                        "Zahiriddin Muhammad Bobur hayoti va faoliyatiga bag'ishlangan tarixiy roman.",
                        "1-bob\n\nAndijon qasrida hokimiyat uchun kurash..."
                    )
                );
                bookRepository.saveAll(defaultBooks);
            }
        };
    }
}