package wad.eightball;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@Points("W2E03")
public class EightBallBlackboxTest {

    private WebDriver driver;
    private String port;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }

    @Test
    public void rootPageHasInputBox() {
        driver.get("http://localhost:" + port + "/");

        driver.findElement(By.name("question"));
    }

    @Test
    public void submitFormDirectsToAPageWithTheGivenQuestion() throws Throwable {
        driver.get("http://localhost:" + port + "/");
        driver.findElement(By.name("question")).sendKeys("carrots?");
        driver.findElement(By.name("question")).submit();

        assertTrue("When a question is submitted, the page should contain the question text!", driver.getPageSource().contains("carrots?"));
    }

    @Test
    public void submitFormDirectsToAPageWithArbitraryGivenQuestion() throws Throwable {
        driver.get("http://localhost:" + port + "/");
        String question = UUID.randomUUID().toString();
        driver.findElement(By.name("question")).sendKeys(question);
        driver.findElement(By.name("question")).submit();

        assertTrue("When a question is submitted, the result page should contain the question text!", driver.getPageSource().contains(question));
    }

    @Test
    public void canSubmitFromAnswerPage() throws Throwable {
        submitFormDirectsToAPageWithArbitraryGivenQuestion();

        String question = UUID.randomUUID().toString();
        driver.findElement(By.name("question")).sendKeys(question);
        driver.findElement(By.name("question")).submit();

        assertTrue("When a question is submitted from the answer page, the result page should contain the question text!", driver.getPageSource().contains(question));
    }

    @Test
    public void answerPageHasOnlyOneAnswer() throws Throwable {
        submitFormDirectsToAPageWithArbitraryGivenQuestion();
        List<String> allAnswers = Arrays.asList(new HardcodedEightballService().getAllAnswers());

        String source = driver.getPageSource();

        int answerCount = 0;
        for (String answer : allAnswers) {
            if (source.contains(answer)) {
                answerCount++;
            }
        }

        assertTrue("The answer page should contain only one answer!", answerCount == 1);
    }

    @Test
    public void manyTypesOfAnswersAreSeen() throws Throwable {
        List<String> allAnswers = Arrays.asList(new HardcodedEightballService().getAllAnswers());
        Set<String> seenAnswers = new TreeSet<String>();

        for (int i = 0; i < 10; i++) {
            submitFormDirectsToAPageWithArbitraryGivenQuestion();
            String source = driver.getPageSource();
            for (String answer : allAnswers) {
                if (source.contains(answer)) {
                    seenAnswers.add(answer);
                    break;
                }
            }
        }

        assertTrue("The answers shown to the user should be randomly selected from the available answers!", seenAnswers.size() > 3);
    }
}
