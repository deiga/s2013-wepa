package wad.datatables;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import javax.persistence.Id;
import org.eclipse.persistence.annotations.Index;
import org.junit.Test;
import static org.junit.Assert.*;
import wad.indexem.domain.Book;

@Points("W5E07")
public class DataTablesTest {

    @Test
    public void verifyFields() {
        for (Field field : Book.class.getDeclaredFields()) {
            if(field.getAnnotation(Id.class) != null) {
                assertNull("No need to index the id -- it's typically handled by the database.", field.getAnnotation(Index.class));
                continue;
            }
            
            Annotation at = field.getAnnotation(Index.class);
            if (at == null) {
                fail("In this exercise you simply have to add the @Index-annotation from eclipselink to most of the fields in the Book class. ");
            }
        }
    }
}