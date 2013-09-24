package wad.hangman;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Points("W3E03.1")
public class HangmanControllerTest {
    
    @Test
    public void testClassAndAnnotationsAndHangmanGameServiceInPlace() {
        Class hangmanControllerClass = ReflectionUtils.findClass("wad.hangman.HangmanController");
        assertNotNull("Verify that the class HangmanController has been generated to the package wad.hangman.", hangmanControllerClass);
    
        assertNotNull("Class HangmanController should have the Controller-annotation.", hangmanControllerClass.getAnnotation(Controller.class));
        
        Field[] fields = hangmanControllerClass.getDeclaredFields();
        assertTrue("The HangmanController needs only one field.", fields.length == 1);
        
        Field isField = fields[0];
        assertTrue("The field in HangmanController should be of type HangmanGameService.", isField.getType().equals(HangmanGameService.class));
        assertNotNull("The HangmanGameService field in HangmanController should have the Autowired annotation.", isField.getAnnotation(Autowired.class));
    }
}