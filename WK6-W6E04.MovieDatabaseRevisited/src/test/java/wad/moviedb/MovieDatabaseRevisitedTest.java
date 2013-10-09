package wad.moviedb;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class MovieDatabaseRevisitedTest {

    private String port;
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        baseUrl = "http://localhost:" + port;
    }

    @Test
    @Points("W6E04")
    public void isEscaped() throws Exception {
        driver.get(baseUrl + "/app/actors");
        WebElement element = null;
        try {
            element = driver.findElement(By.tagName("script"));
        } catch (Throwable t) {
        }
        assertNull("Add c:out-tags to escape content from the actors page.", element);

        driver.findElement(By.tagName("a")).click();
        try {
            element = driver.findElement(By.tagName("script"));
        } catch (Throwable t) {
        }
        assertNull("Add c:out-tags to escape content from the actor page.", element);

        driver.get(baseUrl + "/app/movies");
        try {
            element = driver.findElement(By.tagName("script"));
        } catch (Throwable t) {
        }
        assertNull("Add c:out-tags to escape content from the movies page.", element);

        driver.findElement(By.tagName("a")).click();
        try {
            element = driver.findElement(By.tagName("script"));
        } catch (Throwable t) {
        }
        assertNull("Add c:out-tags to escape content from the web page.", element);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private boolean isTextPresent(String text) {
        try {
            return driver.getPageSource().contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}
