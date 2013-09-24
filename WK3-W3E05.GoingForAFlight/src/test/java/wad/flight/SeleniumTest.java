package wad.flight;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

@Points("W3E05.2")
public class SeleniumTest {

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
    public void testAddingAircraftsToAirports() throws Exception {
        driver.get(baseUrl + "/app/");
        driver.findElement(By.linkText("Aircraft management")).click();

        assertNotPresent("Lolcraft");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Lolcraft");
        driver.findElement(By.id("capacity")).clear();
        driver.findElement(By.id("capacity")).sendKeys("42");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Lolcraft");
        
        assertNotPresent("Batman");
        driver.findElement(By.linkText("Airports")).click();
        assertNotPresent("Batman");
        
        assertNotPresent("Lolcraft");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Batman Airport");
        driver.findElement(By.id("identifier")).clear();
        driver.findElement(By.id("identifier")).sendKeys("BAL");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Batman");
        
        assertNotPresent("Lolcraft");
        driver.findElement(By.linkText("Aircrafts")).click();
        driver.findElement(By.cssSelector("li > form > input[type=\"submit\"]")).click();
        assertPresent("Lolcraft at Batman Airport");
        
        assertNotPresent("Bananaboat");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Bananaboat");
        driver.findElement(By.id("capacity")).clear();
        driver.findElement(By.id("capacity")).sendKeys("3");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertPresent("Bananaboat");
        
        driver.findElement(By.linkText("Airports")).click();
        assertPresent("Lolcraft");
        assertNotPresent("Bananaboat");
        assertNotPresent("Helsinki");

        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Helsinki Airport");
        driver.findElement(By.id("identifier")).clear();
        driver.findElement(By.id("identifier")).sendKeys("HEL");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        assertPresent("Helsinki");
        assertPresent("Lolcraft");
        assertNotPresent("Lolcraft at Batman Airport");
        driver.findElement(By.linkText("Aircrafts")).click();
        new Select(driver.findElement(By.name("airportId"))).selectByVisibleText("Helsinki Airport");
        driver.findElement(By.xpath("//input[@value='Assign']")).click();
        assertPresent("Bananaboat at Helsinki Airport");
        assertPresent("Lolcraft at Batman Airport");
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
            String source = driver.getPageSource();
            source = source.replaceAll("\\s+", " ");
            return source.contains(text);
        } catch (Exception e) {
            return false;
        }
    }
}
