package wad.auth;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.junit.Assert.*;
import org.springframework.security.authentication.AuthenticationProvider;

public class WadAuthenticationProviderTest {

    private WebDriver driver;
    private String baseUri;

    @Before
    public void setup() {
        String port = System.getProperty("jetty.port", "8090");
        if (port == null) {
            port = "8090";
        }
        baseUri = "http://localhost:" + port;
        driver = new HtmlUnitDriver(true);
    }

    @Test
    @Points("W6E03")
    public void hasClassWadAuthenticationProviderThatImplementsCorrectInterface() {
        assertTrue("You should have a class called wad.auth.WadAuthenticationProvider that implements interface AuthenticationProvider.", Reflex.reflect("wad.auth.WadAuthenticationProvider").inherits(AuthenticationProvider.class));
    }

    @Test
    @Points("W6E03")
    public void accessingSecretWithoutAuthenticationDisplaysLoginPage() {
        driver.get(baseUri + "/j_spring_security_logout");
        driver.get(baseUri + "/app/secret");
        assertTrue("Accessing path " + baseUri + "/app/secret should display the login page.", currentPageHasText("login"));
    }

    @Test
    @Points("W6E03")
    public void accessingSecretWithIncorrectDetailsDisplaysLoginPage() {
        accessingSecretWithoutAuthenticationDisplaysLoginPage();
        enterDetailsAndSubmit("robocop", "123");
        assertTrue("After submitting incorrect details, secret should not be shown.", !currentPageHasText("topsiikrt1"));
    }

    @Test
    @Points("W6E03")
    public void accessingSecretWithCorrectDetailsDisplaysSecret() {
        accessingSecretWithoutAuthenticationDisplaysLoginPage();
        enterDetailsAndSubmit("nsa", "nsa");
        assertTrue("After submitting correct details, secret text should be shown.", currentPageHasText("topsiikrt1"));
    }

    private boolean currentPageHasText(String text) {
        return driver.getPageSource().toLowerCase().contains(text.toLowerCase());
    }

    private void enterDetailsAndSubmit(String username, String password) {
        driver.findElement(By.name("j_username")).sendKeys(username);
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.name("j_password")).submit();
    }

    private void doPost(String location, String data) {
        try {
            // Send data
            URL url = new URL(location);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
