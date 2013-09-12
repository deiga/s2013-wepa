package wad.chattingwithanna;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.awt.Desktop;
import java.net.URI;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

@Points("W2E04.3")
public class XOnlineControllerTest {
    private HtmlUnitDriver driver;
    private String port;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }

    @Test
    public void listPageIsValid() {
        driver.get("http://localhost:" + port + "/app/list");

        String source = driver.getPageSource();
        assertTrue("When querying url /app/list, the served JSP page should contain text \"Chatting with Anna\" and have a HTML form. Now it contained: \"" + source +"\".",
                source.contains("Chatting with Anna") && source.contains("<form") && source.contains("add-message"));
    }

    @Test
    public void postingMessageWorksCorrectly() {
        String randomMessage = "Message:" + UUID.randomUUID().toString();

        driver.get("http://localhost:" + port + "/app/list");

        String source = driver.getPageSource();
        assertTrue("When querying url /lapp/ist, the served JSP page should contain text \"Chatting with Anna\" and have a HTML form. Now it contained: \"" + source +"\".",
                source.contains("Chatting with Anna") && source.contains("<form") && source.contains("add-message"));

        WebElement messageTextField = driver.findElementById("message");
        messageTextField.sendKeys(randomMessage);

        WebElement submitButton = driver.findElementById("submit");
        submitButton.click();

        // TODO: how to mock ChatBot when running the application in Jetty?

        source = driver.getPageSource();

        assertTrue("When posting the form to /app/add-message, the served " +
                "JSP page should contain the posted message \""  +
                randomMessage + "\" and the name of the bot \"Anna\". " +
                "Now it contained: \"" + source + "\".",
            source.contains(randomMessage) && source.contains("Anna"));

        try {
//            if (Desktop.isDesktopSupported()) {
//                Desktop desktop = Desktop.getDesktop();
//                if (desktop.isSupported(Desktop.Action.BROWSE)) {
//                    URI uri = new URI("http://www.youtube.com/watch?v=Xm96DZAThNk");
//                    desktop.browse(uri);
//                }
//            }
        } catch ( Exception e ) {
            // ignore
        }
    }
}
