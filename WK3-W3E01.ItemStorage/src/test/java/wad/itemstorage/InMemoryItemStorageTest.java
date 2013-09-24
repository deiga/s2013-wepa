package wad.itemstorage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.stereotype.Service;

@Points("W3E01.1")
public class InMemoryItemStorageTest {

    private ItemStorage itemStorage;

    @Before
    public void initiateItemStorage() throws NoSuchMethodException, Throwable {
       itemStorage = createItemStorage();
    }
    
    public static ItemStorage createItemStorage() throws NoSuchMethodException, Throwable {
        Class inMemoryItemStorageClass = ReflectionUtils.findClass("wad.itemstorage.InMemoryItemStorage");
        if (inMemoryItemStorageClass == null) {
            fail("Verify that you have created a class called InMemoryItemStorage in the package wad.itemstorage.");
        }

        if (!ItemStorage.class.isAssignableFrom(inMemoryItemStorageClass)) {
            fail("Verify that the InMemoryItemStorage-class implements the interface ItemStorage.");
        }

        assertNotNull("Verify that your InMemoryItemStorage has the annotation @Service.", inMemoryItemStorageClass.getAnnotation(Service.class));

        ItemStorage itemStorage = (ItemStorage) ReflectionUtils.invokeConstructor(inMemoryItemStorageClass.getConstructor());
        if (itemStorage == null) {
            fail("Verify that you have created a class that implements the ItemStorage-interface, and that you have added the @Service-annotation to it. Also, make sure that it is in a package that the spring configuration has been configured to scan.");
        }
        
        for(Method method: inMemoryItemStorageClass.getDeclaredMethods()) {            
            // call postconstruct method at the beginning
            if (method.getAnnotation(PostConstruct.class) != null) {
                method.setAccessible(true);
                method.invoke(itemStorage);
            }
        }
        
        return itemStorage;
    }

    @Test
    public void testCreateAndReadSingleItem() {
        Item item = new Item();
        String name = UUID.randomUUID().toString().substring(0, 6);
        item.setName(name);
        String description = UUID.randomUUID().toString().substring(0, 6);
        item.setDescription(description);

        item = itemStorage.create(item);
        if (item.getId() == null) {
            fail("When an item is created, an id should be assigned to it.");
        }

        Item another = itemStorage.read(item.getId());
        if (another == null) {
            fail("When an item is created, it should be available afterwards from the ItemStorage with it's id.");
        }

        assertEquals("When a new item is stored to the ItemStorage, it's name should be stored as well.", name, another.getName());

        assertEquals("When a new item is stored to the ItemStorage, it's description should be stored as well.", description, another.getDescription());
    }
    
    
    @Test
    public void testCreateTwoItems() {
        Item item = new Item();
        item.setName(UUID.randomUUID().toString().substring(0, 6));
        item.setDescription(UUID.randomUUID().toString().substring(0, 6));

        item = itemStorage.create(item);
        if (item.getId() == null) {
            fail("When an item is created, an id should be assigned to it.");
        }

        Item another = new Item();
        another.setName(UUID.randomUUID().toString().substring(0, 6));
        another.setDescription(UUID.randomUUID().toString().substring(0, 6));
        another = itemStorage.create(another);

        assertNotEquals("Each created item should receive a unique id. That is, the identifiers of two created items should not be equal.", item.getId(), another.getId());
    }
    
    private void assertNotEquals(String message, Object one, Object two) {
        if(one == null && two != null) {
            fail(message);
        }
        
        if(one != null && two == null) {
            fail(message);
        }
        
        if(one == null && two == null) {
            return;
        }
        
        if(one.equals(two)) {
            fail(message);
        }
    }

    @Test
    public void testReadMissingItem() {
        Item item = itemStorage.read(1231231);
        if (item != null) {
            fail("ItemStorage should return null when attempting to read an item with an id that has not yet been assigned.");
        }
    }

    @Test
    public void testList() {
        List<Item> items = itemStorage.list();
        assertNotNull("The list in ItemStorage should not be null at the beginning.", items);
        assertEquals("The ItemStorage list should be empty at the beginning.", 0, items.size());


        Item item = new Item();
        String name = UUID.randomUUID().toString().substring(0, 6);
        item.setName(name);
        String description = UUID.randomUUID().toString().substring(0, 6);
        item.setDescription(description);

        itemStorage.create(item);
        
        items = itemStorage.list();
        assertEquals("When one item has been added to the ItemStorage, the list should contain 1 item.", 1, items.size());

        item = new Item();
        String anotherName = UUID.randomUUID().toString().substring(0, 6);
        item.setName(anotherName);
        String anotherDescription = UUID.randomUUID().toString().substring(0, 6);
        item.setDescription(anotherDescription);

        itemStorage.create(item);

        items = itemStorage.list();
        assertEquals("When two items have been added to the ItemStorage, the list should contain 2 items.", 2, items.size());
    
        List<String> names = new ArrayList<String>(Arrays.asList(name, anotherName));
        for (Item i : items) {
            if(names.contains(i.getName())) {
                names.remove(i.getName());
            }
        }
        
        assertTrue("The names of the objects in the ItemStorage should not change when more than a single Item is stored.", names.isEmpty());
    }

    @Test
    public void testUpdate() {

        Item item = new Item();
        String name = UUID.randomUUID().toString().substring(0, 6);
        item.setName(name);
        String description = UUID.randomUUID().toString().substring(0, 6);
        item.setDescription(description);
        
        Item result = itemStorage.update(3, item);
        assertNull("If no item exists with a given id, update should return null.", result);
        
        Item createdItem = itemStorage.create(item);
        
        name = UUID.randomUUID().toString().substring(0, 6);
        createdItem.setName(name);
        createdItem.setDescription(null);
        
        Item updated = itemStorage.update(createdItem.getId(), createdItem);
        
        assertNull("When an item is updated and it's description set to null, the returned item should have a null value in description.", updated.getDescription());
        item = itemStorage.read(createdItem.getId());
        assertNull("When an item is updated, the changes to it's description should reflect to items that are read later as well.", item.getDescription());
        
        assertEquals("When an item is updated, the changes to it's name should reflect to items that are read later as well.", name, item.getName());
    }

    @Test
    public void testDelete() {
        Item item = new Item();
        item.setName(UUID.randomUUID().toString().substring(0, 6));
        item.setDescription(UUID.randomUUID().toString().substring(0, 6));

        item = itemStorage.create(item);
        Item another = itemStorage.delete(item.getId());
        
        assertTrue("The delete-method of ItemStorage should return the deleted item.", item.getName().equals(another.getName()));
        assertTrue("The list should be empty after the only item in the ItemStorage has been deleted.", itemStorage.list().isEmpty());
    
        Item nonExisting = itemStorage.delete(13);
        assertNull("Deleting a non-existing item should return null.", nonExisting);
    }
    
    
    @Test
    public void testSmartlyAssignedIds() {
        Item item = new Item();
        item.setName(UUID.randomUUID().toString().substring(0, 6));
        item.setDescription(UUID.randomUUID().toString().substring(0, 6));

        item = itemStorage.create(item);
        
        Item second = new Item();
        second.setName(UUID.randomUUID().toString().substring(0, 6));
        second.setDescription(UUID.randomUUID().toString().substring(0, 6));

        second = itemStorage.create(second);
        
        itemStorage.delete(item.getId());
        
        Item third = new Item();
        third.setName(UUID.randomUUID().toString().substring(0, 6));
        third.setDescription(UUID.randomUUID().toString().substring(0, 6));

        third = itemStorage.create(third);
        
        
        assertFalse("What is wrong with your current identifier generation technique?", second.getId() == third.getId());
    }
}