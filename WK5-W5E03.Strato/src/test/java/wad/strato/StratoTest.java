package wad.strato;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class StratoTest {

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
    @Points("W5E03.1")
    public void addingObservationPoints() {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Observation points")).click();
        assertFalse(driver.getPageSource().contains("Kumpula Campus"));
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Kumpula Campus");
        driver.findElement(By.id("latitude")).clear();
        driver.findElement(By.id("latitude")).sendKeys("60203744");
        driver.findElement(By.id("longitude")).clear();
        driver.findElement(By.id("longitude")).sendKeys("24961941");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertTrue(driver.getPageSource().contains("Kumpula Campus"));
        assertFalse(driver.getPageSource().contains("Beppu University"));
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("Beppu University");
        driver.findElement(By.id("longitude")).clear();
        driver.findElement(By.id("longitude")).sendKeys("131609300");
        driver.findElement(By.id("latitude")).clear();
        driver.findElement(By.id("latitude")).sendKeys("33239600");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertTrue(driver.getPageSource().contains("Beppu University"));
    }

    @Test
    @Points("W5E03.2")
    public void addingObservationsToPoints() {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Observations")).click();
        assertFalse(driver.getPageSource().contains("11 Celsius"));
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("11");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertTrue(driver.getPageSource().contains("11 Celsius"));
        assertFalse(driver.getPageSource().contains("Beppu University: 23 Celsius"));
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Beppu University");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("23");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertTrue(driver.getPageSource().contains("Beppu University: 23 Celsius"));
        assertFalse(driver.getPageSource().contains("Beppu University: 60 Celsius"));
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Beppu University");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("60");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        assertTrue(driver.getPageSource().contains("11 Celsius"));
        assertTrue(driver.getPageSource().contains("Beppu University: 23 Celsius"));
        assertTrue(driver.getPageSource().contains("Beppu University: 60 Celsius"));
    }

    @Test
    @Points("W5E03.3 W5E03.4")
    public void addingObservationsWithPaging() {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Observations")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("1");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("2");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("3");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("4");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("5");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("7");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("8");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("11");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("12");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("15");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        new Select(driver.findElement(By.id("observationPointId"))).selectByVisibleText("Kumpula Campus");
        driver.findElement(By.id("celsius")).clear();
        driver.findElement(By.id("celsius")).sendKeys("53");
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        driver.findElement(By.linkText("Next")).click();
        assertFalse(driver.getPageSource().contains("Kumpula Campus: 15 Celsius"));
        assertTrue(driver.getPageSource().contains("Kumpula Campus: 4 Celsius"));
        assertFalse(driver.getPageSource().contains("Kumpula Campus: 53 Celsius"));
        driver.findElement(By.linkText("Next")).click();
        assertFalse(driver.getPageSource().contains("Kumpula Campus: 7 Celsius"));
        assertTrue(driver.getPageSource().contains("Kumpula Campus: 11 Celsius"));
        assertFalse(driver.getPageSource().contains("Kumpula Campus: 53 Celsius"));
        driver.findElement(By.linkText("Prev")).click();
        assertTrue(driver.getPageSource().contains("Kumpula Campus: 7 Celsius"));
        assertFalse(driver.getPageSource().contains("Kumpula Campus: 53 Celsius"));
        driver.findElement(By.linkText("Prev")).click(); 
        assertTrue(driver.getPageSource().contains("Kumpula Campus: 53 Celsius"));
        assertFalse(driver.getPageSource().contains("Kumpula Campus: 7 Celsius"));

    }
}
