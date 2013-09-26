package wad.datatables.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.datatables.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByTitleContaining(String search);

    List<Book> findByTitleContaining(String queryString, Pageable pageable);
}
