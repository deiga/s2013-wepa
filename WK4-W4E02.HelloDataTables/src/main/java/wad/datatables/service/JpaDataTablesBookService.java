package wad.datatables.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.datatables.domain.Book;
import wad.datatables.repository.BookRepository;
import wad.datatables.view.DataTablesResponse;

@Service
public class JpaDataTablesBookService implements DataTablesBookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public DataTablesResponse getBooks(String queryString, Integer page) {

// get books with a title that contains the search word
        List<Book> books;
        Pageable sortByTitleAsc = new PageRequest(page, 10, Sort.Direction.ASC, "title");
        books = bookRepository.findByTitleContaining(queryString, sortByTitleAsc);


// get elements to show
        int totalDisplayRecords = books.size();
        int totalElements = Math.min(10, books.size());

        // build response (this you can use even after the refactoring!)
        DataTablesResponse response = new DataTablesResponse();
        response.setTotalRecords(totalElements);
        response.setTotalDisplayRecords(totalDisplayRecords);
        response.setData(books);

        return response;
    }
}
