package wad.rest;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import javax.validation.Valid;
import org.fest.reflect.core.Reflection;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import wad.rest.controller.SleepController;
import wad.rest.domain.Sleep;
import wad.rest.service.SleepService;


@Points("W4E06.4")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
public class D_ControllerTest extends TestHelper {

    private final String SLEEP_CONTROLLER_CLASSNAME = "wad.rest.controller.RESTSleepController";
    private final String SLEEP_REPOSITORY_CLASSNAME = "wad.rest.repository.SleepRepository";
    @Autowired
    private SleepController sleepController;
    @Autowired
    @Qualifier("sleepRepository")
    private CrudRepository<Sleep, Long> sleepRepository;
    private Sleep sleep;

    private String port;
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Throwable {
        driver = new HtmlUnitDriver();
        port = System.getProperty("jetty.port", "8090");
        baseUrl = "http://localhost:" + port + "/app";

        sleep = new Sleep();

        setData(sleep, new Date(), new Date(), "Wicked!");

        sleep = sleepRepository.save(sleep);
    }


    @Test
    public void exists() {

        Reflex.reflect(SLEEP_CONTROLLER_CLASSNAME);
    }

    @Test
    public void extendsSleepService() {

        assertTrue("Class " + SLEEP_CONTROLLER_CLASSNAME + " should extend interface " + SleepController.class.getName() + ".", SleepController.class.isAssignableFrom(Reflex.reflect(SLEEP_CONTROLLER_CLASSNAME).cls()));
    }

    @Test
    public void hasControllerAnnotation() {

        classHasOnlyAnnotations(Reflex.reflect(SLEEP_CONTROLLER_CLASSNAME).cls(), Controller.class);
    }

    @Test
    public void usesSleepService() throws NoSuchFieldException {

        Field[] fields = Reflex.reflect(SLEEP_CONTROLLER_CLASSNAME).cls().getDeclaredFields();

        boolean found = false;

        for (Field field : fields) {

            if (field.getType().equals(SleepService.class)) {
                fieldIsPrivate(field);
                fieldHasOnlyAnnotations(field, Autowired.class);
                found = true;
            }
        }

        assertTrue("You should use SleepService as a field in class " + SLEEP_CONTROLLER_CLASSNAME + ".", found);
    }

    @Test
    public void correctAnnotationsForMethodCreate() throws NoSuchMethodException {

        Class sleepControllerClass = ReflectionUtils.findClass(SLEEP_CONTROLLER_CLASSNAME);

        Method postSleepMethod = Reflection.method("create").withParameterTypes(Model.class, Sleep.class, BindingResult.class)
                .in(sleepControllerClass)
                .info();

        methodHasRequestMappingDefinedWith(postSleepMethod, "sleeps", RequestMethod.POST);

        assertTrue("Parameter sleep in method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a " + Valid.class.getName() + " annotation.", (postSleepMethod.getParameterAnnotations()[1].length != 0 && postSleepMethod.getParameterAnnotations()[1][0].annotationType().equals(Valid.class)));
        assertTrue("Parameter sleep in method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a " + ModelAttribute.class.getName() + " annotation.", (postSleepMethod.getParameterAnnotations()[1].length != 0 && postSleepMethod.getParameterAnnotations()[1][1].annotationType().equals(ModelAttribute.class)));

        ModelAttribute modelAttribute = (ModelAttribute) postSleepMethod.getParameterAnnotations()[1][1];
        assertTrue(ModelAttribute.class.getName() + " annotation on parameter sleep in method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a value sleep.", modelAttribute.value().equals("sleep"));
    }

    @Test
    public void correctAnnotationsForMethodRead() throws NoSuchMethodException {

        Class sleepControllerClass = ReflectionUtils.findClass(SLEEP_CONTROLLER_CLASSNAME);

        Method getSleepMethod = Reflection.method("read").withParameterTypes(Model.class, Long.class)
                .in(sleepControllerClass)
                .info();

        methodHasRequestMappingDefinedWith(getSleepMethod, "sleeps/{id}", RequestMethod.GET);

        assertTrue("Parameter id in method read in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a " + PathVariable.class.getName() + " annotation.", (getSleepMethod.getParameterAnnotations()[1].length != 0 && getSleepMethod.getParameterAnnotations()[1][0].annotationType().equals(PathVariable.class)));

        PathVariable pathVariable = (PathVariable) getSleepMethod.getParameterAnnotations()[1][0];
        assertTrue(PathVariable.class.getName() + " annotation on parameter id in method read in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a value id.", pathVariable.value().equals("id"));
    }

    @Test
    public void correctAnnotationsForMethodDelete() throws NoSuchMethodException {

        Class sleepControllerClass = ReflectionUtils.findClass(SLEEP_CONTROLLER_CLASSNAME);

        Method deleteSleepMethod = Reflection.method("delete").withParameterTypes(Long.class)
                .in(sleepControllerClass)
                .info();

        methodHasRequestMappingDefinedWith(deleteSleepMethod, "sleeps/{id}", RequestMethod.DELETE);

        assertTrue("Parameter id in method delete in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a " + PathVariable.class.getName() + " annotation.", (deleteSleepMethod.getParameterAnnotations()[0].length != 0 && deleteSleepMethod.getParameterAnnotations()[0][0].annotationType().equals(PathVariable.class)));

        PathVariable pathVariable = (PathVariable) deleteSleepMethod.getParameterAnnotations()[0][0];
        assertTrue(PathVariable.class.getName() + " annotation on parameter id in  method delete in class " + SLEEP_CONTROLLER_CLASSNAME + " should have a value id.", pathVariable.value().equals("id"));
    }

    @Test
    public void correctAnnotationsForMethodList() throws NoSuchMethodException {

        Class sleepControllerClass = ReflectionUtils.findClass(SLEEP_CONTROLLER_CLASSNAME);

        Method getSleeps = Reflection.method("list").withParameterTypes(Model.class)
                .in(sleepControllerClass)
                .info();

        methodHasRequestMappingDefinedWith(getSleeps, "sleeps", RequestMethod.GET);
    }

    private MockMvc createControllerWithViewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return standaloneSetup(sleepController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void methodCreateRedirectsCorrectlyWhenSleepIsValid() throws Exception {

        try {
            createControllerWithViewResolver().perform(post("/sleeps")
                    .param("start", "1.9.2012 21.30")
                    .param("end", "2.9.2012 21.30")
                    .param("feeling", "Wicked!"))
                    .andExpect(redirectedUrl("/app/sleeps"));
        } catch (Throwable throwable) {
            Assert.fail("Method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should redirect to \"/app/sleeps\" when Sleep is valid.");
        }
    }

    @Test
    public void methodCreateReturnsCorrectViewWhenSleepIsNotValid() throws Exception {

        try {
            createControllerWithViewResolver().perform(post("/sleeps")
                    .param("start", "Trol")
                    .param("end", "2.9.2012 21.30"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/index.jsp"));
        } catch (Throwable throwable) {
            Assert.fail("Method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should return \"index\" when Sleep is not valid.");
        }
    }

    @Test
    public void methodCreateShouldAddSleepToModelWhenSleepIsNotValid() {

        Sleep sleep = new Sleep();
        Model modelMock = Mockito.mock(Model.class);
        BindingResult resultMock = Mockito.mock(BindingResult.class);

        Mockito.when(resultMock.hasErrors()).thenReturn(true);

        sleepController.create(modelMock, sleep, resultMock);

        try {
            Mockito.verify(resultMock).hasErrors();
        } catch (Throwable throwable) {
            Assert.fail("Method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should check if BindingResult has errors.");
        }

        try {
            Mockito.verify(modelMock).addAttribute(Mockito.eq("sleep"), Mockito.any(Sleep.class));
        } catch (Throwable throwable) {
            Assert.fail("Method create in class " + SLEEP_CONTROLLER_CLASSNAME + " should add non valid sleep to model.");
        }
    }

    @Test
    public void methodReadReturnsCorrectView() throws Exception {

        try {
            createControllerWithViewResolver().perform(get("/sleeps/1"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/sleep.jsp"));
        } catch (Throwable throwable) {
            Assert.fail("Method read in class " + SLEEP_CONTROLLER_CLASSNAME + " should return \"sleep\".");
        }
    }

    @Test
    public void methodReadShouldAddSleepToModel() {

        Model modelMock = Mockito.mock(Model.class);
        sleepController.read(modelMock, new Long(1));

        try {
            Mockito.verify(modelMock).addAttribute(Mockito.eq("sleep"), Mockito.any(Sleep.class));
        } catch (Throwable throwable) {
            Assert.fail("Method read in class " + SLEEP_CONTROLLER_CLASSNAME + " should add a sleep to the model.");
        }
    }

    @Test
    public void methodDeleteRedirectsCorrectly() throws Exception {

        try {
            createControllerWithViewResolver().perform(delete("/sleeps/" + getId(sleep)))
                    .andExpect(redirectedUrl("/app/sleeps"));
        } catch (Throwable throwable) {
            Assert.fail("Method delete in class " + SLEEP_CONTROLLER_CLASSNAME + " should redirect to \"/app/sleeps\".");
        }
    }

    @Test
    public void methodListReturnsCorrectView() throws Exception {

        try {
            createControllerWithViewResolver().perform(get("/sleeps"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/sleeps.jsp"));
        } catch (Throwable throwable) {
            Assert.fail("Method list in class " + SLEEP_CONTROLLER_CLASSNAME + " should return \"sleeps\".");
        }
    }

    @Test
    public void methodListShouldAddListToModel() {

        Model modelMock = Mockito.mock(Model.class);
        sleepController.list(modelMock);

        try {
            Mockito.verify(modelMock).addAttribute(Mockito.eq("sleeps"), Mockito.anyList());
        } catch (Throwable throwable) {
            Assert.fail("Method list in class " + SLEEP_CONTROLLER_CLASSNAME + " should add a list of Sleeps to the model.");
        }
    }

    private void setData(Sleep sleep, Date start, Date end, String feeling) throws Throwable {
        Reflex.reflect(Sleep.class).method("setStart").returningVoid().taking(Date.class).invokeOn(sleep, start);
        Reflex.reflect(Sleep.class).method("setEnd").returningVoid().taking(Date.class).invokeOn(sleep, end);
        Reflex.reflect(Sleep.class).method("setFeeling").returningVoid().taking(String.class).invokeOn(sleep, feeling);
    }

    private Long getId(Sleep sleep) throws Throwable {
        return Reflex.reflect(Sleep.class).method("getId").returning(Long.class).takingNoParams().invokeOn(sleep);
    }



    
    @After
    public void tearDown() throws Exception {
        driver.quit();
        sleepRepository.deleteAll();

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

    private void assertPresent(String text) {
        assertTrue("The page should contain text: " + text + "\nCurrent source:\n" + driver.getPageSource(), isTextPresent(text));
    }

    private void assertNotPresent(String text) {
        assertFalse("The page should not contain text: " + text + "\nCurrent source:\n" + driver.getPageSource(), isTextPresent(text));
    }
    
    @Test
    public void formForAddingSleepPresent() {
        
        driver.get(baseUrl + "/");
        
        driver.findElement(By.id("start"));
        driver.findElement(By.id("end"));
        driver.findElement(By.id("feeling"));
    }
    
    @Test
    public void testAddingValidSleep() {
        
        driver.get(baseUrl + "/");
        
        driver.findElement(By.id("start")).clear();
        driver.findElement(By.id("start")).sendKeys("1.9.2012 21.30");
        
        driver.findElement(By.id("end")).clear();
        driver.findElement(By.id("end")).sendKeys("2.9.2012 21.30");
        
        driver.findElement(By.id("feeling")).clear();
        driver.findElement(By.id("feeling")).sendKeys("Wicked!");
        
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        
        assertPresent("Sleep with ID 1");
    }
    
    @Test
    public void testAddingNonValidSleep() {
        
        driver.get(baseUrl + "/");
        
        driver.findElement(By.id("start")).clear();
        driver.findElement(By.id("start")).sendKeys("Trol");
        
        driver.findElement(By.id("end")).clear();
        driver.findElement(By.id("end")).sendKeys("2.9.2012 21.30");
        
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        
        assertPresent("New sleep");
    }
    
    @Test
    public void testDeletingSleep() {
        
        testAddingValidSleep();
        
        driver.get(baseUrl + "/sleeps");
        
        driver.findElement(By.partialLinkText("Sleep with ID 1")).click();
        
        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
        
        assertNotPresent("Sleep with ID 1");
    }

}