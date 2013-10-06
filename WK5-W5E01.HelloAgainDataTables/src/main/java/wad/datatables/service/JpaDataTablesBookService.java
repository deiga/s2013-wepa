package wad.datatables.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public DataTablesResponse getBooks(String queryString) {
        Pageable pageable = new PageRequest(0, 10, Sort.Direction.ASC, "title");
        Page<Book> page = bookRepository.findByTitleContaining(queryString, pageable);
        
        DataTablesResponse response = new DataTablesResponse();
        response.setTotalRecords(page.getTotalElements());
        response.setTotalDisplayRecords(page.getNumberOfElements());
        response.setData(page.getContent());
        
        return response;
    }
}
