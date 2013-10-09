package wad.parlezvousfrancais;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.Locale;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Points("W6E05.2")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
public class B_LocalisationTest {

    private static final String[] KEYS = {
        "label.question",
        "label.yes",
        "label.no",
        "label.answer",
        "label.answers",
        "label.great",
        "label.answerAgain",
        "label.thatsOkay"
    };

    private WebDriver driver;
    private String port;
    private String baseUrl;

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Throwable {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        baseUrl = "http://localhost:" + port + "/app";
    }

    protected String[] getMessages(MessageSource messageSource, Locale locale, String[] keys) {

        String[] messages = new String[keys.length];

        for (int i = 0; i < keys.length; i++) {
            messages[i] = messageSource.getMessage(keys[i], null, locale);
        }

        return messages;
    }

    @Test
    public void testDefinedProperties() {

        MessageSource messageSource;
        try {
            messageSource = applicationContext.getBean(MessageSource.class);
        } catch (Exception e) {
            fail("Verify that you have configured ReloadableResourceBundleMessageSource in Spring ApplicationContext. Error: " + e.toString());
            return;
        }

        Locale finnish = new Locale("fi");
        String[] finnishMessages = getMessages(messageSource, finnish, KEYS);

        Locale english = new Locale("en");
        String[] englishMessages = getMessages(messageSource, english, KEYS);

        Locale french = new Locale("fr");
        String[] frenchMessages = getMessages(messageSource, french, KEYS);

        assertFalse("Verify that you have translated the messages into Finnish.", Arrays.equals(finnishMessages, englishMessages));
        assertFalse("Verify that you have translated the messages into Finnish.", Arrays.equals(finnishMessages, frenchMessages));

        assertFalse("Verify that you have translated the messages into English.", Arrays.equals(englishMessages, finnishMessages));
        assertFalse("Verify that you have translated the messages into English.", Arrays.equals(englishMessages, frenchMessages));

        assertFalse("Verify that you have translated the messages into French.", Arrays.equals(frenchMessages, finnishMessages));
        assertFalse("Verify that you have translated the messages into French.", Arrays.equals(frenchMessages, englishMessages));
    }

    private boolean isTextPresent(String text) {
        try {
            String source = driver.getPageSource();
            source = source.replaceAll("\\s+", " ");
            return source.contains(text);
        } catch (Exception e) {
            return false;
        }
    }

    private void assertPresent(String text, String locale) {
        assertTrue("The page for " + locale + " locale should contain text: " + text + "\nCurrent source:\n" + driver.getPageSource(), isTextPresent(text));
    }

    private void assertNotPresent(String text, String locale) {
        assertFalse("The page for " + locale + " locale should not contain text: " + text + "\nCurrent source:\n" + driver.getPageSource(), isTextPresent(text));
    }

    @Test
    public void changeLanguageFromFrenchToEnglish() {

        driver.get(baseUrl + "/?locale=fr");

        assertNotPresent("French", "French");
        assertPresent("Français", "French");

        driver.get(baseUrl + "/?locale=en");

        assertNotPresent("Français", "English");
        assertPresent("French", "English");
    }

    @Test
    public void changeLanguageFromFrenchToFinnish() {

        driver.get(baseUrl + "/?locale=fr");

        assertNotPresent("ranskaa", "French");
        assertPresent("Français", "French");

        driver.get(baseUrl + "/?locale=fi");

        assertNotPresent("Français", "Finnish");
        assertPresent("ranskaa", "Finnish");
    }

    @Test
    public void testFrenchLocale() {

        driver.get(baseUrl + "/?locale=fr");

        assertPresent("Français", "French");
        assertPresent("Oui", "French");
        assertPresent("Non", "French");
        assertPresent("Répondre", "French");

        assertPresent("réponses", "French");

        WebElement element = driver.findElement(By.xpath("//input[@name='answer' and @value='yes']"));
        element.click();
        element.submit();

        assertPresent("Magnifique!", "French");
        assertPresent("Répondre encore", "French");

        driver.get(baseUrl + "/");

        element = driver.findElement(By.xpath("//input[@name='answer' and @value='no']"));
        element.click();
        element.submit();

        assertPresent("Ce n'est pas grave!", "French");
        assertPresent("Répondre encore", "French");
    }
}