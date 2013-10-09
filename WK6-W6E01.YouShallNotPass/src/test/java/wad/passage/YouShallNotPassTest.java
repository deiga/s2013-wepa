package wad.passage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@Points("W6E01")
public class YouShallNotPassTest {

    private WebDriver driver;
    private String baseUri;

    @Before
    public void setup() {
        String port = System.getProperty("jetty.port", "8090");
        if (port == null) {
            port = "8090";
        }
        baseUri = "http://localhost:" + port;
        driver = new HtmlUnitDriver();
    }

    @Test
    public void accessingIndexWorks() {
        driver.get(baseUri);
        assertTrue("No text \"To the secret pages!\" found on the page. Pagesource:\n" + driver.getPageSource(), currentPageHasText("To the secret pages!"));
    }

    @Test
    public void accessingPublicImagesWorks() {
        driver.get(baseUri + "/public/images/no-pass.jpeg");
        assertTrue("Everything under the path " + baseUri + "/public should be accessible directly.", driver.getCurrentUrl().contains("/public/images/no-pass.jpeg"));
    }

    @Test
    public void accessingAppDirectsToLogin() {
        driver.get(baseUri + "/app/awesome");
        assertTrue("Everything under the path " + baseUri + "/app should not be accessible directly. Pagesource:\n" + driver.getPageSource(), currentPageHasText("login"));

        enterDetailsAndSubmit("lolo", "haha");
        assertTrue("Everything under the path " + baseUri + "/app should not be accessible without correct credentials.", currentPageHasText("login"));

        enterDetailsAndSubmit("mikael", "rendezvous");
        assertFalse("Everything under the path " + baseUri + "/app should be accessible after entering correct credentials.", currentPageHasText("login"));
        assertTrue("Accessing " + baseUri + "/app/awesome and writing correct credentials should allow the user to see the view from PassageController.", currentPageHasText("Yeehaw"));
    }

    @Test
    public void accessingSubfolderUnderAppDirectsToLogin() {
        driver.get(baseUri + "/app/trololo/awesome");
        assertTrue("Everything under the path " + baseUri + "/app should not be accessible directly. Pagesource:\n" + driver.getPageSource(), currentPageHasText("login"));

        enterDetailsAndSubmit("lolo", "haha");
        assertTrue("Everything under the path " + baseUri + "/app should not be accessible without correct credentials.", currentPageHasText("login"));

        enterDetailsAndSubmit("mikael", "rendezvous");
        assertFalse("Everything under the path " + baseUri + "/app should be accessible after entering correct credentials.", currentPageHasText("login"));
        assertTrue("Accessing subfolder under " + baseUri + "/app and writing correct credentials should show the user a 404 error as the controller does not monitor a subfolder.", currentPageHasText("404"));
    }

    @Test
    public void accessingSecretAsMikke() {
        accessingSecret("mikael", "rendezvous");
    }

    @Test
    public void accessingRandomThingsGives403() {
        driver.get(baseUri + "/" + UUID.randomUUID().toString());
        assertTrue("Everything else in the app should not be accessible directly.", currentPageHasText("login"));

        enterDetailsAndSubmit("lolo", "haha");
        assertTrue("Everything else in the app should not be accessible without correct credentials.", currentPageHasText("login"));

        enterDetailsAndSubmit("mikael", "rendezvous");
        assertFalse("Everything else in the app should not be accessible even after entering correct credentials.", currentPageHasText("login"));
        assertTrue("Everything else in the app should not be accessible even after entering correct credentials.", currentPageHasText("403"));
    }

    @Test
    public void accessingRandomSubfolderThingsGives403() {
        driver.get(baseUri + "/" + UUID.randomUUID().toString() + "/" + UUID.randomUUID().toString());
        assertTrue("Everything else in the app should not be accessible directly.", currentPageHasText("login"));

        enterDetailsAndSubmit("lolo", "haha");
        assertTrue("Everything else in the app should not be accessible without correct credentials.", currentPageHasText("login"));

        enterDetailsAndSubmit("mikael", "rendezvous");
        assertFalse("Everything else in the app should not be accessible even after entering correct credentials.", currentPageHasText("login"));
        assertTrue("Everything else in the app should not be accessible even after entering correct credentials.", currentPageHasText("403"));
    }

    @Test
    public void accessingSecretAsKasper() {
        accessingSecret("kasper", "spring");
    }

    private void enterDetailsAndSubmit(String username, String password) {
        driver.findElement(By.name("j_username")).sendKeys(username);
        driver.findElement(By.name("j_password")).sendKeys(password);
        driver.findElement(By.name("j_password")).submit();
    }

    private boolean currentPageHasText(String text) {
        return driver.getPageSource().toLowerCase().contains(text.toLowerCase());
    }

    private void accessingSecret(String username, String password) {
        driver.get(baseUri + "/secret/images/guessed.jpg");
        assertTrue("Everything under the path " + baseUri + "/secret should not be accessible directly.", currentPageHasText("login"));

        enterDetailsAndSubmit("lolo", "haha");
        assertTrue("Everything under the path " + baseUri + "/secret should not be accessible without correct credentials.", currentPageHasText("login"));

        enterDetailsAndSubmit(username, password);
        assertFalse("Everything under the path " + baseUri + "/secret should be accessible after entering correct credentials.", currentPageHasText("login"));
        assertFalse("Everything under the path " + baseUri + "/secret should be accessible after entering correct credentials.", currentPageHasText("403"));
    }
}
