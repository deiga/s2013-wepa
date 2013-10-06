package wad.indexem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.indexem.domain.Book;

public interface BookRepository extends JpaRepository<Book, String> {
    Page<Book> findByPublicationYear(Integer publicationYear, Pageable pageable);
    Page<Book> findByTitle(String title, Pageable pageable);
    Page<Book> findByIsbn(String isbn, Pageable pageable);
    Page<Book> findByImprint(String imprint, Pageable pageable);
}
