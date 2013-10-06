package wad.indexem.service;

import wad.indexem.view.DataTablesResponse;

public interface DataTablesBookService {
    DataTablesResponse getBooks(String queryString, String field);
}
