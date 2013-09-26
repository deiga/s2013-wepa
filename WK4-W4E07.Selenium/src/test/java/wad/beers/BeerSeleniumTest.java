package wad.beers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
}
