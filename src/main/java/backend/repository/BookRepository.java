package backend.repository;

import backend.model.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<EBook, Long> {

    // Faqat katalog (kategoriya) bo'yicha saralash
    List<EBook> findByCategory(String category);

    // Nomi, muallifi yoki janri bo'yicha qidirish
    List<EBook> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(
            String title, String author, String genre);

    // Nomi va katalogi bo'yicha birga qidirish
    List<EBook> findByTitleContainingIgnoreCaseAndCategory(String title, String category);

    // Muallifi va katalogi bo'yicha birga qidirish
    List<EBook> findByAuthorContainingIgnoreCaseAndCategory(String author, String category);
}