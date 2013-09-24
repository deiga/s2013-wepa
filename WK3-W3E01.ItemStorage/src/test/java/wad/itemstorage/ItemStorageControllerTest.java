
package wad.itemstorage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Points("W3E01.2")
public class ItemStorageControllerTest {
    
    @Test
    public void testClassAndAnnotationsAndItemStorageInPlace() {
        Class itemStorageControllerClass = ReflectionUtils.findClass("wad.itemstorage.ItemStorageController");
        assertNotNull("Verify that the class ItemStorageController has been generated to the package wad.itemstorage.", itemStorageControllerClass);
    
        assertNotNull("Class ItemStorageController should have the Controller-annotation.", itemStorageControllerClass.getAnnotation(Controller.class));
        
        Field[] fields = itemStorageControllerClass.getDeclaredFields();
        assertTrue("The ItemStorageController needs only one field.", fields.length == 1);
        
        Field isField = fields[0];
        assertTrue("The field in ItemStorageController should be of type ItemStorage.", isField.getType().equals(ItemStorage.class));
        assertNotNull("The ItemStorage field in ItemStorageController should have the Autowired annotation.", isField.getAnnotation(Autowired.class));
    }
}