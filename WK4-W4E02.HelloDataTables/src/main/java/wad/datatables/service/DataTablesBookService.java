package wad.datatables.service;

import wad.datatables.view.DataTablesResponse;

public interface DataTablesBookService {
    DataTablesResponse getBooks(String queryString, Integer page);
}
