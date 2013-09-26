package wad.rest;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.fest.reflect.core.Reflection;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import wad.rest.domain.Sleep;
import wad.rest.service.SleepService;

@Points("W4E06.3")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
public class C_ServiceTest extends TestHelper {

    private final String SLEEP_SERVICE_CLASSNAME = "wad.rest.service.JpaSleepService";
    private final String SLEEP_REPOSITORY_CLASSNAME = "wad.rest.repository.SleepRepository";
    
    @Autowired
    private SleepService sleepService;
    
    @Autowired
    @Qualifier("sleepRepository")
    private CrudRepository<Sleep, Long> sleepRepository;

    @Test
    public void exists() {
        
        Reflex.reflect(SLEEP_SERVICE_CLASSNAME);
    }

    @Test
    public void extendsSleepService() {
        
        assertTrue("Class " + SLEEP_SERVICE_CLASSNAME + " should extend interface " + SleepService.class.getName() + ".", SleepService.class.isAssignableFrom(Reflex.reflect(SLEEP_SERVICE_CLASSNAME).cls()));
    }
    
    @Test
    public void hasServiceAnnotation() {
        
        classHasOnlyAnnotations(Reflex.reflect(SLEEP_SERVICE_CLASSNAME).cls(), Service.class);
    }
    
    @Test
    public void usesSleepRepository() throws NoSuchFieldException {
        
        Field[] fields = Reflex.reflect(SLEEP_SERVICE_CLASSNAME).cls().getDeclaredFields();
        
        boolean found = false;
        
        for (Field field : fields) {
            
            if (field.getType().equals(Reflex.reflect(SLEEP_REPOSITORY_CLASSNAME).cls())) {
                fieldIsPrivate(field);
                fieldHasOnlyAnnotations(field, Autowired.class);
                found = true;
            }
        }
        
        assertTrue("You should use SleepRepository as a field in class " + SLEEP_SERVICE_CLASSNAME + ".", found);
    }
    
    @Test
    public void correctAnnotationsForMethodCreate() throws NoSuchMethodException {
        
        Class sleepServiceClass = ReflectionUtils.findClass(SLEEP_SERVICE_CLASSNAME);
        
        Method save = Reflection.method("create").withParameterTypes(Sleep.class)
                                                 .in(sleepServiceClass)
                                                 .info();
        
        methodHasTransactionalDefinedWith(save, false);
    }
    
    @Test
    public void correctAnnotationsForMethodRead() throws NoSuchMethodException {
        
        Class sleepServiceClass = ReflectionUtils.findClass(SLEEP_SERVICE_CLASSNAME);
        
        Method findOne = Reflection.method("read").withParameterTypes(Long.class)
                                                  .in(sleepServiceClass)
                                                  .info();
        
        methodHasTransactionalDefinedWith(findOne, true);
    }
    
    @Test
    public void correctAnnotationsForMethodDelete() throws NoSuchMethodException {
        
        Class sleepServiceClass = ReflectionUtils.findClass(SLEEP_SERVICE_CLASSNAME);
        
        Method delete = Reflection.method("delete").withParameterTypes(Long.class)
                                                   .in(sleepServiceClass)
                                                   .info();
        
        methodHasTransactionalDefinedWith(delete, false);
    }
    
    @Test
    public void correctAnnotationsForMethodList() throws NoSuchMethodException {
        
        Class sleepServiceClass = ReflectionUtils.findClass(SLEEP_SERVICE_CLASSNAME);
        
        Method findAll = Reflection.method("list").withParameterTypes()
                                                  .in(sleepServiceClass)
                                                  .info();
        
        methodHasTransactionalDefinedWith(findAll, true);
    }
    
    @Test
    public void savedSleepFoundById() throws Throwable {
        
        Sleep sleep = new Sleep();
        setData(sleep, new Date(), new Date(), "Awesome!");
        
        sleep = sleepService.create(sleep);
        
        assertNotNull("Saved sleep should be found by method read from " + SLEEP_SERVICE_CLASSNAME + ".", sleepService.read(getId(sleep)));
    }
    
    @Test
    public void deletedSleepShouldNotBeFoundById() throws Throwable {
        
        Sleep sleep = new Sleep();
        setData(sleep, new Date(), new Date(), "Awesome!");
        
        sleep = sleepService.create(sleep);
        
        sleepService.delete(getId(sleep));
        
        assertNull("Deleted sleep should not be found by method read from " + SLEEP_SERVICE_CLASSNAME + ".", sleepService.read(getId(sleep)));
    }
    
    @Test
    public void findAllShouldReturnAllSleeps() throws Throwable {
        
        sleepRepository.deleteAll();
        
        Sleep a = new Sleep();
        setData(a, new Date(), new Date(), "Awesome!");

        Sleep b = new Sleep();
        setData(b, new Date(), new Date(), "Wicked!");
        
        Sleep c = new Sleep();
        
        setData(c, new Date(), new Date(), "Fantastic!");
        
        a = sleepService.create(a);
        b = sleepService.create(b);
        c = sleepService.create(c);
        
        List<Sleep> expectedSleeps = new ArrayList<Sleep>();
        Collections.addAll(expectedSleeps, new Sleep[] {a, b, c});
        
        List<Sleep> actualSleeps = sleepService.list();
        
        assertEquals("Expected method list to return 3 sleeps from " + SLEEP_SERVICE_CLASSNAME + ".", 3, actualSleeps.size());
        
        int found = 0;
        for (Sleep actualSleep : actualSleeps) {
            
            for (Sleep expectedSleep : expectedSleeps) {
                
                if (getId(expectedSleep).equals(getId(actualSleep))) {
                    
                    found++;
                    continue;
                }
            }
        }
        
        assertEquals("Expected method list to return 3 sleeps with correct ids from " + SLEEP_SERVICE_CLASSNAME + ".", 3, found);
    }
    
    private void setData(Sleep sleep, Date start, Date end, String feeling) throws Throwable {
        Reflex.reflect(Sleep.class).method("setStart").returningVoid().taking(Date.class).invokeOn(sleep, start);
        Reflex.reflect(Sleep.class).method("setEnd").returningVoid().taking(Date.class).invokeOn(sleep, end);
        Reflex.reflect(Sleep.class).method("setFeeling").returningVoid().taking(String.class).invokeOn(sleep, feeling);
    }
    
    private Long getId(Sleep sleep) throws Throwable {
        return Reflex.reflect(Sleep.class).method("getId").returning(Long.class).takingNoParams().invokeOn(sleep);
    }
}