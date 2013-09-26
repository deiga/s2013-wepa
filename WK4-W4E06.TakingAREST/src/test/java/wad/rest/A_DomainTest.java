package wad.rest;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.format.annotation.DateTimeFormat;

@Points("W4E06.1")
public class A_DomainTest {
    
    private final String DATE_FORMAT = "d.M.y H.m";
    private final String SLEEP_CLASSNAME = "wad.rest.domain.Sleep";
    
    private final Map<String, Class> sleepAttributesAndTypes = new TreeMap<String, Class>() {
        {
            put("id", Long.class);
            put("start", Date.class);
            put("end", Date.class);
            put("feeling", String.class);
        }
    };
    
    private final Map<String, List<Class>> sleepAttributeAnnotations = new TreeMap<String, List<Class>>() {
        {
            put("id", wad.rest.EntityTester.createAnnotationList(Id.class, GeneratedValue.class, Column.class));
            put("start", wad.rest.EntityTester.createAnnotationList(Temporal.class, Column.class, DateTimeFormat.class, NotNull.class));
            put("end", wad.rest.EntityTester.createAnnotationList(Temporal.class, Column.class, DateTimeFormat.class, NotNull.class));
            put("feeling", wad.rest.EntityTester.createAnnotationList(Column.class, NotBlank.class));
        }
    };
    
    @Test
    public void validateSleep() {
        new wad.rest.EntityTester().testEntity(SLEEP_CLASSNAME, sleepAttributesAndTypes, sleepAttributeAnnotations);
    }
    
    @Test
    public void correctGeneratedValueStrategyForFieldId() throws NoSuchFieldException {
        
        assertTrue("Field id in class " + SLEEP_CLASSNAME + " should have GenerationType.AUTO as the strategy for annotation " + GeneratedValue.class.getName() + ".", Reflex.reflect(SLEEP_CLASSNAME).cls().getDeclaredField("id").getAnnotation(GeneratedValue.class).strategy().toString().equals("AUTO"));
    }
    
    @Test
    public void correctTemporalTypeForFieldStart() throws NoSuchFieldException {
        
        assertTrue("Field start in class " + SLEEP_CLASSNAME + " should have Temporal.DATE as the value for annotation " + Temporal.class.getName() + ".", Reflex.reflect(SLEEP_CLASSNAME).cls().getDeclaredField("start").getAnnotation(Temporal.class).value().toString().equals("DATE"));
    }
    
    @Test
    public void correctDateTimeFormatForFieldStart() throws NoSuchFieldException {
        
        assertTrue("Field start in class " + SLEEP_CLASSNAME + " should have " + DATE_FORMAT + " as the pattern for annotation " + DateTimeFormat.class.getName() + ".", Reflex.reflect(SLEEP_CLASSNAME).cls().getDeclaredField("start").getAnnotation(DateTimeFormat.class).pattern().toString().equals(DATE_FORMAT));
    }
    
    @Test
    public void correctTemporalTypeForFieldEnd() throws NoSuchFieldException {
        
        assertTrue("Field end in class " + SLEEP_CLASSNAME + " should have Temporal.DATE as the value for annotation " + Temporal.class.getName() + ".", Reflex.reflect(SLEEP_CLASSNAME).cls().getDeclaredField("end").getAnnotation(Temporal.class).value().toString().equals("DATE"));
    }
    
    @Test
    public void correctDateTimeFormatForFieldEnd() throws NoSuchFieldException {
        
        assertTrue("Field end in class " + SLEEP_CLASSNAME + " should have " + DATE_FORMAT + " as the pattern for annotation " + DateTimeFormat.class.getName() + ".", Reflex.reflect(SLEEP_CLASSNAME).cls().getDeclaredField("end").getAnnotation(DateTimeFormat.class).pattern().toString().equals(DATE_FORMAT));
    }
}