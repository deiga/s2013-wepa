package wad.indexem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.indexem.domain.Book;
import wad.indexem.repository.BookRepository;
import wad.indexem.view.DataTablesResponse;

@Service
public class JpaDataTablesBookService implements DataTablesBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public DataTablesResponse getBooks(String queryString, String field) {
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.ASC, "title");

        Page<Book> page = null;
        if ("title".equals(field)) {
            page = bookRepository.findByTitle(queryString, pageable);
        } else if ("isbn".equals(field)) {
            page = bookRepository.findByIsbn(queryString, pageable);
        } else if ("imprint".equals(field)) {
            page = bookRepository.findByImprint(queryString, pageable);
        } else if ("publicationYear".equals(field)) {
            page = bookRepository.findByPublicationYear(Integer.parseInt(queryString), pageable);
        } else {
            page = bookRepository.findAll(pageable);
        }

        
        long totalElements = page.getTotalElements();
        long totalDisplayRecords = page.getNumberOfElements();
        List<Book> books = page.getContent();

        // build response (this you can use even after the refactoring!)
        DataTablesResponse response = new DataTablesResponse();
        response.setTotalRecords(totalElements);
        response.setTotalDisplayRecords(totalDisplayRecords);
        response.setData(books);

        return response;
    }
}
