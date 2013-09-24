package wad.storage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import wad.storage.controller.StorageControllerInterface;

@Points("W3E04.3")
public class StorageControllerTest {

    private final String STORAGE_CONTROLLER_CLASSNAME = "wad.storage.controller.StorageController";
    private final String ITEM_REPOSITORY_CLASSNAME = "wad.storage.repository.ItemRepository";

    @Test
    public void exists() {
        Reflex.reflect(STORAGE_CONTROLLER_CLASSNAME);
    }

    @Test
    public void isAController() {
        assertNotNull("Class " + STORAGE_CONTROLLER_CLASSNAME + " should have annotation " + Controller.class.getName() + ".", Reflex.reflect(STORAGE_CONTROLLER_CLASSNAME).cls().getAnnotation(Controller.class));
    }

    @Test
    public void implementsInterface() {
        assertTrue("Class " + STORAGE_CONTROLLER_CLASSNAME + " should implement interface " + StorageControllerInterface.class.getName() + ".",
                StorageControllerInterface.class.isAssignableFrom(Reflex.reflect(STORAGE_CONTROLLER_CLASSNAME).cls()));
    }

    @Test
    public void containsItemRepositoryThatIsAutowired() {
        Field itemRepositoryField = null;

        Class itemRepositoryClass = Reflex.reflect(ITEM_REPOSITORY_CLASSNAME).cls();

        for (Field field : Reflex.reflect(STORAGE_CONTROLLER_CLASSNAME).cls().getDeclaredFields()) {
            if (field.getType().isAssignableFrom(itemRepositoryClass)) {
                itemRepositoryField = field;
                break;
            }
        }

        assertNotNull("Class " + STORAGE_CONTROLLER_CLASSNAME + " should have a field " + ITEM_REPOSITORY_CLASSNAME, itemRepositoryField);
        assertNotNull("Field " + ITEM_REPOSITORY_CLASSNAME + " in class " + STORAGE_CONTROLLER_CLASSNAME + " should be annotated with " + Autowired.class.getName() + ".", itemRepositoryField.getAnnotation(Autowired.class));
    }

    @Test
    public void pageFlowWorks() {
        WebDriver driver = new HtmlUnitDriver();
        String port = System.getProperty("jetty.port", "8090");

        driver.get("http://localhost:" + port + "/app/view");

        String name = UUID.randomUUID().toString().substring(0, 8);
        WebElement nameElement = driver.findElement(By.id("name"));
        nameElement.sendKeys(name);
        nameElement.submit();

        String source = driver.getPageSource().toLowerCase();
        assertTrue("After submitting a item using the given form, the item should be visible on the page.", source.contains(name));

        // extra 1 is for the :
        String cString = source.substring(source.indexOf(name) + name.length() + 1);
        cString = cString.trim();
        int count = Integer.parseInt(cString.substring(0, 1));
        assertTrue("Count should start from one.", count == 1);

        List<WebElement> elements = driver.findElements(By.tagName("input"));
        WebElement incrementButton = null;
        for (WebElement element : elements) {
            String id = element.getAttribute("id");
            if (id == null || !id.startsWith("increment")) {
                continue;
            }

            incrementButton = element;
            break;
        }

        incrementButton.submit();

        source = driver.getPageSource().toLowerCase();
        assertTrue("After submitting a item using the given form, the item should be visible on the page.", source.contains(name));

        // extra 1 is for the :
        cString = source.substring(source.indexOf(name) + name.length() + 1);
        cString = cString.trim();
        count = Integer.parseInt(cString.substring(0, 1));
        assertTrue("After clicking the increment button once, the count should be two.", count == 2);


        // mass add!
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            name = UUID.randomUUID().toString().substring(0, 8);
            nameElement = driver.findElement(By.id("name"));
            nameElement.sendKeys(name);
            nameElement.submit();
            
            names.add(name);
        }
        
        
        source = driver.getPageSource().toLowerCase();
        for(String n: names) {
            if(!source.contains(n)) {
                fail("Item with name " + n + " was added to the page, but it was not shown. Current source: " + source);
            }
        }
    }
}
