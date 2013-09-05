package wad.myalbums;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class MyAlbumsTest {

    private String listJspSource;
    private List<Album> albums;

    @Before
    public void setUp() throws FileNotFoundException {
        String fileName = "src/main/webapp/WEB-INF/jsp/list.jsp";
        Scanner sc = new Scanner(new File(fileName));

        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            sb.append(sc.nextLine());
        }

        sc.close();
        this.listJspSource = sb.toString();
        this.albums = new ListServlet().generateAlbums();
    }

    @Test
    @Points("W1E08.1")
    public void rendezvousParkAlbumNamesNotInListJsp() {
        for (Album album : this.albums) {
            if (this.listJspSource.contains(album.getName())) {
                fail("The list.jsp-file should not contain the album names directly. They should be added using JSTL. File list.jsp contained " + album.getName());
            }
        }
    }

    @Test
    @Points("W1E08.1")
    public void rendezvousParkAlbumNamesInRenderedPage() {
        WebDriver driver = new HtmlUnitDriver();
        String port = System.getProperty("jetty.port", "8090");

        String listUri = "http://localhost:" + port + "/list";
        driver.get(listUri);

        String source = driver.getPageSource();

        for (Album album : albums) {
            if (!source.contains(album.getName())) {
                fail("The page at /list should contain album name " + album.getName() + ", now it didn't. Page source was: " + source);
            }
        }
    }

    @Test
    @Points("W1E08.2")
    public void rendezvousParkSongNamesNotInListJsp() {
        for (Album album : this.albums) {
            for (String track : album.getTracks()) {
                if (this.listJspSource.contains(track)) {
                    fail("The list.jsp-file should not contain the track names directly. They should be added using JSTL. File list.jsp contained " + track);
                }
            }
        }
    }
    
    
    @Test
    @Points("W1E08.2")
    public void rendezvousParkSongNamesInRenderedPage() {
        WebDriver driver = new HtmlUnitDriver();
        String port = System.getProperty("jetty.port", "8090");

        String listUri = "http://localhost:" + port + "/list";
        driver.get(listUri);

        String source = driver.getPageSource();

        for (Album album : albums) {
            for (String track : album.getTracks()) {
                if (!source.contains(track)) {
                    fail("The page at /list should contain track name " + track + ", now it didn't. Page source was: " + source);
                }
            }
        }
    }

}
