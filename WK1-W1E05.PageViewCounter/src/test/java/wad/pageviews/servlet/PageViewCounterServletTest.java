package wad.pageviews.servlet;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.http.HttpServlet;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

@Points("W1E05")
public class PageViewCounterServletTest {

    private Class servletClass;
    
    private WebDriver driver;
    private String port;

    @Before
    public void setUp() {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
    }
    
    @Test
    public void classPageViewCounterServletExists() {
        servletClass = ReflectionUtils.findClass("wad.pageviews.servlet.PageViewCounterServlet");
    }
    
    
    @Test
    public void extendsHttpServlet() {
        classPageViewCounterServletExists();
        assertTrue("Class PageViewCounterServlet must extends javax.servlet.http.HttpServlet", HttpServlet.class.isAssignableFrom(servletClass));
    }


    @Test
    public void submitFormDirectsToCorrectPage() throws Throwable {
        String uri = "http://localhost:" + port + "/count";
        driver.get(uri);

        String source = driver.getPageSource();
        source = source.toLowerCase();
        assertTrue("The page at \"/count\" should contain text \"Pyyntöjä:\"", source.contains("pyynt") && source.contains(":"));

        List<Integer> numbers = getNumbers(source);
        if (!numbers.contains(new Integer(1))) {
            fail("Number 1 should be shown at the first visit.");
        }

        for (int i = 0; i < 10; i++) {
            driver.get(uri);

            List<Integer> newNumbers = getNumbers(driver.getPageSource());

            boolean atLeastOneGrew = false;
            for (int numIdx = 0; numIdx < numbers.size(); numIdx++) {
                if (newNumbers.get(numIdx) == numbers.get(numIdx) + 1) {
                    atLeastOneGrew = true;
                    break;
                }
            }

            numbers = newNumbers;

            if (!atLeastOneGrew) {
                fail("The visit count should grow by one for each visit.");
            }
        }
    }

    private List<Integer> getNumbers(String text) {
        String nums = text.replaceAll("[^\\d]", " ");
        nums = nums.replaceAll("\\s+", " ");
        List<Integer> numbers = new ArrayList<Integer>();

        Scanner sc = new Scanner(nums);
        while (sc.hasNextInt()) {
            numbers.add(sc.nextInt());
        }

        return numbers;
    }
}
