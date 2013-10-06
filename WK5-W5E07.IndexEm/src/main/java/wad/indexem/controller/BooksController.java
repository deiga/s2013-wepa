package wad.indexem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.indexem.service.DataTablesBookService;
import wad.indexem.view.DataTablesResponse;

@Controller
public class BooksController {

    @Autowired
    private DataTablesBookService bookService;

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseBody
    public DataTablesResponse getBooks(@RequestParam(value = "query", defaultValue = "2012") String queryString,
            @RequestParam(value = "field", defaultValue = "publicationYear") String field) {
        return bookService.getBooks(queryString, field);
    }
}
