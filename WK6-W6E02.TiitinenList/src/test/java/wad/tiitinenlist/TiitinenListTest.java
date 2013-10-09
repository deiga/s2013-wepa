package wad.tiitinenlist;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.junit.Assert.*;
import org.openqa.selenium.JavascriptExecutor;

public class TiitinenListTest {

    private WebDriver driver;
    private String baseUri;

    @Before
    public void setup() {
        String port = System.getProperty("jetty.port", "8090");
        if (port == null) {
            port = "8090";
        }
        baseUri = "http://localhost:" + port;
        driver = new HtmlUnitDriver(true);
    }

    @Test
    @Points("W6E02.1")
    public void accessingListAsMedia() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));

        enterDetailsAndSubmit("media", "media");
        assertTrue("After submitting correct details, Tiitinen List should be shown. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Tiitinen List"));

        assertTrue("When role is media, \"Susanna Reinboth\" should be the only list item. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Susanna Reinboth"));
        assertFalse("When role is media, \"Susanna Reinboth\" should be the only list item. Pagesource:\n" + driver.getPageSource(), currentPageHasText("ADAM"));
        assertFalse("When role is media, user should not be able to see the add-button. Pagesource:\n" + driver.getPageSource(), currentPageHasText("add"));
    }

    @Test
    @Points("W6E02.1")
    public void accessingListAsSupo() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));

        enterDetailsAndSubmit("robocop", "123");
        assertTrue("After submitting correct details, Tiitinen List should be shown. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Tiitinen List"));

        assertFalse("When role is supo, \"Susanna Reinboth\" should not be visible. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Susanna Reinboth"));
        assertTrue("When role is supo, real list should be visible. Pagesource:\n" + driver.getPageSource(), currentPageHasText("ADAM"));
        assertTrue("When role is supo, user should be able to see the add-button. Pagesource:\n" + driver.getPageSource(), currentPageHasText("add"));
        assertFalse("When role is supo, user should not be able to see the delete-button. Pagesource:\n" + driver.getPageSource(), currentPageHasText("delete"));

        driver.findElement(By.name("content")).sendKeys("hello supo!");
        driver.findElement(By.name("content")).submit();
        assertTrue("After submitting new content, it should be visible in the list. Submitted \"hello world!\", pagesource:\n" + driver.getPageSource(), currentPageHasText("hello supo"));
    }

    @Test
    @Points("W6E02.1")
    public void accessingListAsTiitinen() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));

        enterDetailsAndSubmit("seppo", "tiitinen");
        assertTrue("After submitting correct details, Tiitinen List should be shown. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Tiitinen List"));

        assertFalse("When role is admin, \"Susanna Reinboth\" should not be visible. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Susanna Reinboth"));
        assertTrue("When role is admin, real list should be visible. Pagesource:\n" + driver.getPageSource(), currentPageHasText("ADAM"));
        assertTrue("When role is admin, user should be able to see the add-button. Pagesource:\n" + driver.getPageSource(), currentPageHasText("add"));
        assertTrue("When role is admin, user should be able to see the delete-button. Pagesource:\n" + driver.getPageSource(), currentPageHasText("delete"));


        driver.findElement(By.name("content")).sendKeys("hello seppo!");
        driver.findElement(By.name("content")).submit();
        assertTrue("After submitting new content, it should be visible in the list. Submitted \"hello world!\", pagesource:\n" + driver.getPageSource(), currentPageHasText("hello seppo"));
    }

    @Test
    @Points("W6E02.2")
    public void createNotAvailableAsMedia() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));
        enterDetailsAndSubmit("media", "media");

        assertTrue("After submitting correct details, Tiitinen List should be shown. Pagesource:\n" + driver.getPageSource(), currentPageHasText("Tiitinen List"));

        boolean found = false;
        try {
            driver.findElement(By.name("content"));
            found = true;
        } catch (Exception e) {
        }

        if (found) {
            fail("After logging in as media, the form for adding new items should not be available. Pagesource:\n" + driver.getPageSource());
        }

        assertFalse("After logging in as media, the form for adding new items should not be available. Pagesource:\n" + driver.getPageSource(), currentPageHasText("POST"));

        // hack-hackiti-hack-hack-hack -- server-side xss :D
        String postForm = "<form method=post action=" + baseUri + "/app/list>"
                + "<input type=text name=content id=trollcontent>"
                + "<input type=submit>"
                + "</form>";
        String js = "var el = document.createElement('div');"
                + "el.innerHTML=\"" + postForm + "\"; document.body.appendChild(el);";
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(js);

        driver.findElement(By.name("content")).sendKeys("hello media!");
        driver.findElement(By.id("trollcontent")).submit();

        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));
        enterDetailsAndSubmit("robocop", "123");
        assertFalse("User authenticated as \"media\" should not be able to POST new content to the page.", currentPageHasText("hello media"));
    }
    
    
    @Test
    @Points("W6E02.2")
    public void deleteNotAvailableAsMedia() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));
        enterDetailsAndSubmit("media", "media");
        
        int id = 3;
        createAndExecuteDeleteForm(3);
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));
        enterDetailsAndSubmit("seppo", "tiitinen");
        assertTrue("User authenticated as \"media\" should not be able to DELETE existing data from the page.", currentPageHasText("/app/list/" + id));
    }
    
    @Test
    @Points("W6E02.2")
    public void deleteNotAvailableAsSupo() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        enterDetailsAndSubmit("robocop", "123");
        int id = 1;
        createAndExecuteDeleteForm(id);
        driver.get(baseUri + "/app/list");
        
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        assertTrue("Accessing path " + baseUri + "/app/list should display the login page.", currentPageHasText("login"));
        enterDetailsAndSubmit("seppo", "tiitinen");
        
        assertTrue("User authenticated as \"supo\" should not be able to DELETE existing data from the page. " + driver.getPageSource(), currentPageHasText("/app/list/" + id));
    }
    
    
    
    @Test
    @Points("W6E02.2")
    public void deleteAvailableAsSeppo() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/list");
        enterDetailsAndSubmit("seppo", "tiitinen");
        int id = 2;
        createAndExecuteDeleteForm(id);
        assertFalse("User authenticated as \"admin\" should be able to DELETE existing data from the page.", currentPageHasText("/app/list/" + id));
    }
    
    private void createAndExecuteDeleteForm(int id) {
        
        // server-side xss :o
        String postForm = "<form method=post action='" + baseUri + "/app/list/" + id + "'>"
                + "<input type=hidden name=_method value=DELETE>"
                + "<input type=submit>"         
                + "<input type=hidden id='troll-" + id + "'>"
                + "</form>";
        String js = "var el = document.createElement('div');"
                + "el.innerHTML=\"" + postForm + "\"; document.body.appendChild(el);";
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(js);

        driver.findElement(By.id("troll-" + id)).submit();
    }

    private boolean currentPageHasText(String text) {
        return driver.getPageSource().toLowerCase().contains(text.toLowerCase());
    }

    private void enterDetailsAndSubmit(String username, String password) {
        driver.findElement(By.name("j_username")).sendKeys(username);
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.name("j_password")).submit();
    }

    private void doPost(String location, String data) {
        try {
            // Send data
            URL url = new URL(location);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
