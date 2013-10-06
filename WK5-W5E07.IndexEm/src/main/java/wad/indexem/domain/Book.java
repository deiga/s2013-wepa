package wad.indexem.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Book implements Serializable {

    @Id
    private String isbn;
    private String title;
    private Integer publicationYear;
    private String imprint;

    public Book() {
    }

    public Book(String isbn, String title, Integer publicationYear, String imprint) {
        if(isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("Invalid ISBN given: " + isbn);
        }
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.imprint = imprint;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getImprint() {
        return imprint;
    }

    public void setImprint(String imprint) {
        this.imprint = imprint;
    }
}
