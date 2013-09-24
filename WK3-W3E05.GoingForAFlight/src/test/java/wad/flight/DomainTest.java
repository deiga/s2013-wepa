package wad.flight;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("W3E05.1")
public class DomainTest {

    private final String AIRCRAFT_CLASSNAME = "wad.flight.domain.Aircraft";
    private final String AIRPORT_CLASSNAME = "wad.flight.domain.Airport";
    private final Map<String, Class> aircraftAttributeAndType = new TreeMap<String, Class>() {
        {
            put("id", Long.class);
            put("name", String.class);
            put("airport", Reflex.reflect(AIRPORT_CLASSNAME).cls());
        }
    };
    private final Map<String, List<Class>> aircraftAttributeAnnotations = new TreeMap<String, List<Class>>() {
        {
            put("id", EntityTester.createAnnotationList(Id.class, GeneratedValue.class, Column.class));
            put("name", EntityTester.createAnnotationList(Column.class));
            put("airport", EntityTester.createAnnotationList(JoinColumn.class, ManyToOne.class));
        }
    };
    private final Map<String, Class> airportAttributeAndType = new TreeMap<String, Class>() {
        {
            put("id", Long.class);
            put("identifier", String.class);
            put("name", String.class);
            put("aircrafts", List.class);
        }
    };
    private final Map<String, List<Class>> airportAttributeAnnotations = new TreeMap<String, List<Class>>() {
        {
            put("id", EntityTester.createAnnotationList(Id.class, GeneratedValue.class, Column.class));
            put("identifier", EntityTester.createAnnotationList(Column.class));
            put("name", EntityTester.createAnnotationList(Column.class));
            put("aircrafts", EntityTester.createAnnotationList(OneToMany.class));
        }
    };

    @Test
    public void validateAircraft() {
        new EntityTester().testEntity(AIRCRAFT_CLASSNAME, aircraftAttributeAndType, aircraftAttributeAnnotations);
    }

    @Test
    public void validateAirport() {
        new EntityTester().testEntity(AIRPORT_CLASSNAME, airportAttributeAndType, airportAttributeAnnotations);
    }

    @Test
    public void airportToAircraftsOneToManyCorrectlySet() {
        new EntityTester().testEntity(AIRPORT_CLASSNAME, airportAttributeAndType, airportAttributeAnnotations);

        Field aircraftsField = EntityTester.findField(AIRPORT_CLASSNAME, "aircrafts");

        // type correct
        String genericString = aircraftsField.toGenericString();
        assertTrue("Field aircrafts in class " + AIRPORT_CLASSNAME + " should be a list that contains Aircraft-objects, e.g. List<Aircraft>. Now it was: " + genericString, genericString.contains("List") && genericString.contains("Aircraft"));

        Annotation annotation = aircraftsField.getAnnotation(OneToMany.class);
        assertNotNull("Field aircrafts in class " + AIRPORT_CLASSNAME + " should have annotation " + OneToMany.class.getName() + ".", annotation);

        OneToMany oneToManyAnnotation = (OneToMany) annotation;
        assertTrue("Annotation OneToMany for field aircrafts in class " + AIRPORT_CLASSNAME + "\nshould have parameter mappedBy set to \"airport\".", oneToManyAnnotation.mappedBy().equals("airport"));
    }
}
