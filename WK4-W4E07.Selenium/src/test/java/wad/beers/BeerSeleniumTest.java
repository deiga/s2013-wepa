package wad.beers;

import junit.framework.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class BeerSeleniumTest {

    private String port;
    private WebDriver driver;
    private String baseUrl;

    // ATTN! DO NOT CHANGE THE CLASS VARIABLES OR THE setUp-METHOD
    @Before
    public void setUp() throws Exception {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        baseUrl = "http://localhost:" + port;
    }

    @Test
    public void helloWorld() {
        assertNotSame("Hello", "World!");
    }

    // PROGRAM YOUR TESTS HERE!
    
    @Test
    public void submitAndVerify() {
        driver.get(baseUrl+"/beers");
        
        assertFalse(driver.getPageSource().contains("Up Up Down Down Left Right Left Right BA Select"));
        WebElement element = driver.findElement(By.id("name"));
        element.sendKeys("Up Up Down Down Left Right Left Right BA Select");
        element.submit();
        assertTrue(driver.getPageSource().contains("Up Up Down Down Left Right Left Right BA Select"));
        
    }
    
    @Test
    public void submitThreeAndVerify() {
        driver.get(baseUrl+"/beers");
        assertFalse(driver.getPageSource().contains("Gargamel Ale"));
        assertFalse(driver.getPageSource().contains("Crazy Ivan"));
        assertFalse(driver.getPageSource().contains("Hoptimus Prime"));
        
        WebElement element = driver.findElement(By.id("name"));
        element.sendKeys("Gargamel Ale");
        element.submit();
        
        element = driver.findElement(By.id("name"));
        element.sendKeys("Crazy Ivan");
        element.submit();
        
        element = driver.findElement(By.id("name"));
        element.sendKeys("Hoptimus Prime");
        element.submit();
        
        assertTrue(driver.getPageSource().contains("Gargamel Ale"));
        assertTrue(driver.getPageSource().contains("Crazy Ivan"));
        assertTrue(driver.getPageSource().contains("Hoptimus Prime"));
    }
}
