package wad.moviedb;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class MovieDatabaseTest {

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
    @Points("W3E06.1")
    public void testAddAndRemoveActor() throws Exception {
        driver.get(baseUrl + "/app/actors");
        assertNotPresent("Van Damme");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Van Damme");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Van Damme");
        driver.findElement(By.xpath("//li/form/input")).click();
        assertNotPresent("Van Damme");
        assertNotPresent("Chuck Norris");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Chuck Norris");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertNotPresent("Van Damme");
        assertPresent("Chuck Norris");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Van Damme");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Van Damme");
        assertPresent("Chuck Norris");
        driver.findElement(By.xpath("//li[2]/form/input")).click();
        assertNotPresent("Van Damme");
        assertPresent("Chuck Norris");
        driver.findElement(By.xpath("//li[1]/form/input")).click();
        assertNotPresent("Van Damme");
        assertNotPresent("Chuck Norris");
    }

    @Test
    @Points("W3E06.2")
    public void testAddAndRemoveMovie() throws Exception {
        driver.get(baseUrl + "/app/movies");
        assertNotPresent("Bloodsport");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Bloodsport");
        driver.findElement(By.id("lengthInMinutes")).clear();
        driver.findElement(By.id("lengthInMinutes")).sendKeys("92");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Bloodsport");
        driver.findElement(By.xpath("//li/form/input")).click();
        assertNotPresent("Bloodsport");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Bloodsport");
        driver.findElement(By.id("lengthInMinutes")).clear();
        driver.findElement(By.id("lengthInMinutes")).sendKeys("92");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Bloodsport");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Sidekicks");
        driver.findElement(By.id("lengthInMinutes")).clear();
        driver.findElement(By.id("lengthInMinutes")).sendKeys("101");
        driver.findElement(By.xpath("//input[@type='submit']")).click();
        assertPresent("Bloodsport");
        assertPresent("Sidekicks");
        driver.findElement(By.xpath("//li[2]/form/input")).click();
        assertPresent("Bloodsport");
        assertNotPresent("Sidekicks");
        driver.findElement(By.xpath("//li[1]/form/input")).click();
        assertNotPresent("Bloodsport");
        assertNotPresent("Sidekicks");
    }

    @Test
    @Points("W3E06.3 W3E06.4")
    public void testAddMovieAndActorAndAssignFinallyRemoveAll() throws Exception {
        driver.get(baseUrl + "/app/movies");
        assertNotPresent("Bloodsport");
        assertNotPresent("Van Damme");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Bloodsport");
        driver.findElement(By.id("lengthInMinutes")).clear();
        driver.findElement(By.id("lengthInMinutes")).sendKeys("92");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Bloodsport");
        assertNotPresent("Van Damme");
        driver.findElement(By.linkText("Actors")).click();
        assertNotPresent("Van Damme");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Van Damme");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Van Damme");
        driver.findElement(By.linkText("Van Damme")).click();
        driver.findElement(By.id("add-to-movie")).click();
        driver.findElement(By.linkText("Movies")).click();
        assertPresent("Bloodsport");
        assertPresent("Van Damme");
        driver.findElement(By.xpath("//li[1]/form/input")).click();
        assertNotPresent("Bloodsport");
        assertNotPresent("Van Damme");
        driver.findElement(By.linkText("Actors")).click();
        assertPresent("Van Damme");
        assertNotPresent("Bloodsport");
        assertNotPresent("null");
        driver.findElement(By.xpath("//li/form/input")).click();
        assertNotPresent("Van Damme");
        assertNotPresent("Bloodsport");
        assertNotPresent("null");
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    private void assertPresent(String text) {
        assertTrue("The page should contain text: " + text + "\nCurrent source:\n" + driver.getPageSource(), isTextPresent(text));
    }

    private void assertNotPresent(String text) {
        assertFalse("The page should not contain text: " + text + "\nCurrent source:\n" + driver.getPageSource(), isTextPresent(text));
    }

    private boolean isTextPresent(String text) {
        try {
            return driver.getPageSource().contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}
