package wad.datatables.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public DataTablesResponse getBooks(String queryString) {
        
// get books with a title that contains the search word
List<Book> books = new ArrayList<Book>();
if(!queryString.isEmpty()) {
for (Book book : bookRepository.findAll()) {
if(book.getTitle().contains(queryString)) {
books.add(book);
}
}
} else {
books = bookRepository.findAll();
}

// order by title ascending
Collections.sort(books, new Comparator<Book>() {

@Override
public int compare(Book o1, Book o2) {
return o1.getTitle().compareTo(o2.getTitle());
}

});

// get elements to show
int totalDisplayRecords = books.size();
int totalElements = Math.min(10, books.size());
books = books.subList(0, totalElements);
        
        // build response (this you can use even after the refactoring!)
        DataTablesResponse response = new DataTablesResponse();
        response.setTotalRecords(totalElements);
        response.setTotalDisplayRecords(totalDisplayRecords);
        response.setData(books);
        
        return response;
    }
}
