package wad.datatables.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.datatables.service.DataTablesBookService;
import wad.datatables.view.DataTablesResponse;

@Controller
public class BooksController {

    @Autowired
    private DataTablesBookService bookService;

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    @ResponseBody
    public DataTablesResponse getBooks(@RequestParam("sSearch") String queryString, @RequestParam("sEcho") String echo) {
        DataTablesResponse response = bookService.getBooks(queryString);
        response.setEcho(echo);
        return response;
    }
}
