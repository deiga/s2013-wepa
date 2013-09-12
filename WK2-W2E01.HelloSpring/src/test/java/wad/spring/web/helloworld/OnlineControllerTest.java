package wad.spring.web.helloworld;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

@Points("W2E01.2")
public class OnlineControllerTest {
    private WebDriver driver;
    private String port;
    
    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }

    @Test
    public void jspDoesNotContainGreatScottByItself() throws Exception {
        String fileName = "src/main/webapp/WEB-INF/jsp/hello.jsp";
        Scanner sc = new Scanner(new File(fileName));
        
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            
            assertFalse("The file " + fileName + " should not contain text \"Great Scott!\" by itself. " +
                    "The request attribute \"message\" containing value \"Great Scott!\" should " +
                    "be passed from the controller.", line.contains("Great Scott!"));
        }
        
        sc.close();
    }

    @Test
    public void jspContainsMessageAttributeReference() throws Exception {
        String fileName = "src/main/webapp/WEB-INF/jsp/hello.jsp";
        Scanner sc = new Scanner(new File(fileName));

        boolean found = false;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            
            if (line.contains("${message}")) {
                found = true;
                break;
            }
        }
        
        sc.close();

        assertTrue("The file " + fileName + " should contain a reference to the model attribute called \"message\". " +
                "The attribute should be passed from the controller.", found);
    }

    @Test
    public void listensToHello() {
        driver.get("http://localhost:" + port + "/app/hello");
        String source = driver.getPageSource().toLowerCase();
        assertTrue("When querying url /app/hello, the application should return string \"Hello World\". Now it returned \"" + source +"\".", source.contains("hello world"));
        assertTrue("When querying url /app/hello, the application should return the message (Great Scott!) from the request attribute \"message\". Now it returned \"" + source +"\".", source.contains("great scott!"));
    }
}
