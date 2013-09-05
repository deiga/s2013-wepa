package wad.firsthtmlform.servlet;

import fi.helsinki.cs.tmc.edutestutils.Points;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@Points("W1E04")
public class RequestParametersServletTest {

    private WebDriver driver;
    private String port;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }

    @Test
    public void nameFieldExistsOnPage() {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        driver.findElement(By.id("name"));
    }

    @Test
    public void addressFieldExistsOnPage() {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        driver.findElement(By.id("address"));
    }

    @Test
    public void greenRadiobuttonExistsOnPage() {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        WebElement element = driver.findElement(By.id("ticket-green"));
        String type = element.getAttribute("type");
        
        assertNotNull("Radio buttons should have type \"radio\".", type);
        assertEquals("Radio buttons should have type \"radio\".", type, "radio");
    
        String value = element.getAttribute("value");
        assertNotNull("Radio buttons should have a value.", value);
        
        assertEquals("The radio button with id \"ticket-green\" should have value \"green\".", value, "green");

    }

    @Test
    public void yellowRadiobuttonExistsOnPage() {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        WebElement element = driver.findElement(By.id("ticket-yellow"));
        String type = element.getAttribute("type");
        
        assertNotNull("Radio buttons should have type \"radio\".", type);
        assertEquals("Radio buttons should have type \"radio\".", type, "radio");
        
        String value = element.getAttribute("value");
        assertNotNull("Radio buttons should have a value.", value);
        
        assertEquals("The radio button with id \"ticket-yellow\" should have value \"yellow\".", value, "yellow");
    }

    @Test
    public void redRadiobuttonExistsOnPage() {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        WebElement element = driver.findElement(By.id("ticket-red"));
        String type = element.getAttribute("type");
        
        assertNotNull("Radio buttons should have type \"radio\".", type);
        assertEquals("Radio buttons should have type \"radio\".", type, "radio");
        
        String value = element.getAttribute("value");
        assertNotNull("Radio buttons should have a value.", value);
        
        assertEquals("The radio button with id \"ticket-red\" should have value \"red\".", value, "red");
    }
    
    @Test
    public void formSubmitsToView() {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        WebElement element = driver.findElement(By.id("ticket-red"));
        element.submit();
        
        String currentUrl = driver.getCurrentUrl().toLowerCase();
        
        assertTrue("When the form is submitted, it should be sent to the given url.", currentUrl.contains("view"));
    }
    

    @Test
    public void formContainsElementsAndValuesAreSubmittedProperly() throws Throwable {
        String uri = "http://localhost:" + port + "/";
        driver.get(uri);

        driver.findElement(By.id("name")).sendKeys("mikael");
        driver.findElement(By.id("address")).sendKeys("leppavaara");
        
        driver.findElement(By.id("ticket-red")).click();
        
        String source = driver.getPageSource().toLowerCase();
        
        assertTrue("When the form is submitted, given name should be visible on the new page. Verify that your form elements are inside the form tag.", source.contains("mikael"));
        assertTrue("When the form is submitted, given address should be visible on the new page. Verify that your form elements are inside the form tag.", source.contains("leppavaara"));
        assertTrue("When the form is submitted, selected ticket value should be visible on the new page. Verify that your form elements are inside the form tag.", source.contains("red"));
    }
}
