package backend.repository;

import backend.model.EBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<EBook, Long> {
    List<EBook> findByCategory(String category);
    List<EBook> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrGenreContainingIgnoreCase(
            String title, String author, String genre);
}