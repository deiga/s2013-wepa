package wad.indexem.init;

import java.util.Scanner;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import wad.indexem.domain.Book;
import wad.indexem.repository.BookRepository;

@Component
public class InitService {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private BookRepository bookRepository;

    @PostConstruct
    @Transactional(readOnly = false)
    private void init() throws Exception {
        Scanner sc = new Scanner(wac.getResource("classpath:books.csv").getFile());

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) {
                continue;
            }

            String[] data = line.replaceAll("\"", "").split(",");
            if (data.length < 10) {
                continue;
            }


            String isbn = data[3];
            String title = data[4];
            String year = data[5];
            String imprint = data[9];

            if (isbn.isEmpty() || title.isEmpty() || year.isEmpty() || imprint.isEmpty()) {
                continue;
            }

            try {
                Book book = new Book(isbn, title, Integer.parseInt(year), imprint);
                bookRepository.save(book);
            } catch (Throwable t) {
                System.out.println(t.getMessage());
            }
        }
    }
}
