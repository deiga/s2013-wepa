package wad.datatables.view;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import wad.datatables.domain.Book;

public class DataTablesResponse implements Serializable {

    @JsonProperty(value = "iTotalRecords")
    public long totalRecords;
    @JsonProperty(value = "iTotalDisplayRecords")
    public long totalDisplayRecords;
    @JsonProperty(value = "sEcho")
    public String echo;
    @JsonProperty(value = "aaData")
    public List<Book> data = new ArrayList<Book>();

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getTotalDisplayRecords() {
        return totalDisplayRecords;
    }

    public void setTotalDisplayRecords(long totalDisplayRecords) {
        this.totalDisplayRecords = totalDisplayRecords;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public List<Book> getData() {
        return data;
    }

    public void setData(List<Book> data) {
        this.data = data;
    }
}
