package wad.commonitems;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import wad.commonitems.controller.ItemController;
import wad.commonitems.domain.Item;
import wad.commonitems.service.ItemService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@Points("W2E06.2")
public class XControllerTest {

    @Autowired
    private ItemController itemController;

    @Test
    public void testListItems() throws IllegalArgumentException, IllegalAccessException {
        ItemService itemServiceMock = Mockito.mock(ItemService.class);

        // swap the private field in the itemController
        boolean fieldFound = false;
        for (Field field : ItemController.class.getDeclaredFields()) {
            field.setAccessible(true);

            if (ItemService.class.isAssignableFrom(field.getType())) {
                field.set(itemController, itemServiceMock);
                fieldFound = true;

                Autowired wired = field.getAnnotation(Autowired.class);
                if (wired == null) {
                    Assert.fail("The ItemService field of ItemController should be autowired.");
                }
            }
        }

        Assert.assertTrue("ItemController should have a private field ItemService", fieldFound);

        Item rnd = new Item();
        rnd.setName("yea");
        List<Item> items = new ArrayList<Item>();
        items.add(rnd);

        Mockito.when(itemServiceMock.list()).thenReturn(items);

        ExtendedModelMap model = new ExtendedModelMap();
        String listResponse = itemController.listItems(model);

        Assert.assertTrue("When the method listItems of ItemController is called, it should add a list of items with the name \"items\" to the model.", model.containsKey("items"));
        Assert.assertEquals("When ItemController is listing items, it should add the exactly same item list to the model that it receives from the ItemService.", items, model.get("items"));
        Assert.assertEquals("The method listItems of ItemController should return a string \"list\"", "list", listResponse);
    }

    @Test
    public void testAddItem() throws IllegalArgumentException, IllegalAccessException {
        final Item res = new Item();
        ItemService itemServiceMock = new ItemService() {
            @Override
            public void add(Item item) {
                res.setName(item.getName());
                res.setId(item.getId());
                res.setDescription(item.getDescription());
            }

            @Override
            public void delete(Integer id) {
                throw new RuntimeException("You are not supposed to delete when adding.");
            }

            @Override
            public List<Item> list() {
                throw new RuntimeException("You are not supposed to list when adding.");
            }
        };

        // swap the private field in the itemController
        boolean fieldFound = false;
        for (Field field : ItemController.class.getDeclaredFields()) {
            field.setAccessible(true);

            if (ItemService.class.isAssignableFrom(field.getType())) {
                field.set(itemController, itemServiceMock);
                fieldFound = true;

                Autowired wired = field.getAnnotation(Autowired.class);
                if (wired == null) {
                    Assert.fail("The ItemService field of ItemController should be autowired.");
                }
            }
        }

        Assert.assertTrue("ItemController should have a private field ItemService", fieldFound);

        String listResponse = itemController.addItem("yea", "shoes");

        Assert.assertEquals("The method addItem of ItemController should return a string \"redirect:list\"", "redirect:list", listResponse);
        Assert.assertEquals("When calling the addItem method of ItemController, the item added to the ItemService should have the name that the controller received as a parameter.", "yea", res.getName());
        Assert.assertEquals("When calling the addItem method of ItemController, the item added to the ItemService should have the description that the controller received as a parameter.", "shoes", res.getDescription());
    }

    @Test
    public void testDeleteItem() throws IllegalArgumentException, IllegalAccessException {
        final StringBuilder idHolder = new StringBuilder();
        ItemService itemServiceMock = new ItemService() {
            @Override
            public void add(Item item) {
                throw new RuntimeException("You are not supposed to add when deleting.");
            }

            @Override
            public void delete(Integer id) {
                idHolder.append(id);
            }

            @Override
            public List<Item> list() {
                throw new RuntimeException("You are not supposed to list when deleting.");
            }
        };


        // swap the private field in the itemController
        boolean fieldFound = false;
        for (Field field : ItemController.class.getDeclaredFields()) {
            field.setAccessible(true);

            if (ItemService.class.isAssignableFrom(field.getType())) {
                field.set(itemController, itemServiceMock);
                fieldFound = true;

                Autowired wired = field.getAnnotation(Autowired.class);
                if (wired == null) {
                    Assert.fail("The ItemService field of ItemController should be autowired.");
                }
            }
        }

        Assert.assertTrue("ItemController should have a private field ItemService", fieldFound);
        String listResponse = itemController.deleteItem(12);
        Assert.assertEquals("The method deleteItem of ItemController should return a string \"redirect:list\"", "redirect:list", listResponse);
        Assert.assertEquals("When the method deleteItem of ItemController is called, it should call the delete method of the ItemService.", "12", idHolder.toString());
    }
}
