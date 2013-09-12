package wad.passwordgenerator;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.io.File;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static org.junit.Assert.*;

@Points("W2E02.2")
public class OnlineControllerTest {

    private WebDriver driver;
    private String port;
    
    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }

    @Test
    public void jspContainsPasswordAttributeReference() throws Exception {
        String fileName = "src/main/webapp/WEB-INF/jsp/password.jsp";
        Scanner sc = new Scanner(new File(fileName));

        boolean found = false;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            
            if (line.contains("${password}")) {
                found = true;
                break;
            }
        }
        
        sc.close();

        assertTrue("The file " + fileName + " should contain a reference to the model attribute called \"password\". " +
                "The attribute should be passed from the controller.", found);
    }

    @Test
    public void newPasswordReturnsPassword() {
        driver.get("http://localhost:" + port + "/app/new-password");
        String source = driver.getPageSource().toLowerCase();
        assertTrue("When querying url /app/new-password, the served JSP page should contain text \"Password Generator\". Now it contained: \"" + source +"\".",
                source.contains("password generator"));
    }
}
