/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.lovemeter.xxx;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import wad.lovemeter.service.LoveMeterService;

@Points("W1E07.3")
public class XLoveMeterBlackBoxTest {

    private WebDriver driver;
    private String port;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }

    @Test
    public void rootPageHasInputBoxes() {
        driver.get("http://localhost:" + port + "/");

        driver.findElement(By.id("name1"));
        driver.findElement(By.id("name2"));
    }

    @Test
    public void submitFormDirectsToCorrectPage() throws Throwable {
        driver.get("http://localhost:" + port + "/");


        WebElement name1TextBox = driver.findElement(By.id("name1"));
        WebElement name2TextBox = driver.findElement(By.id("name2"));


        String name1 = UUID.randomUUID().toString();
        String name2 = UUID.randomUUID().toString();
        name1TextBox.sendKeys(name1);
        name2TextBox.sendKeys(name2);

        name1TextBox.submit();

        String source = driver.getPageSource();
        assertTrue("When the form on the main page has been submitted, the user should be sent to a page that contains the name from the text field.", source.contains(name1));
        assertTrue("When the form on the main page has been submitted, the user should be sent to a page that contains the name from the text field.", source.contains(name2));


        LoveMeterService lms = (LoveMeterService) Reflex.reflect("wad.lovemeter.service.KumpulaLoveMeter").constructor().takingNoParams().invoke();
        int match = lms.match(name1, name2);

        assertTrue("After the form has been submitted, user should be shown a page with the correct lovemeter-score.love score.", source.contains("" + match));
    }
}
