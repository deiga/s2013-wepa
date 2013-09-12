package wad.commonitems;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import javax.annotation.PostConstruct;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import wad.commonitems.domain.Item;
import wad.commonitems.service.ItemService;

// oldskool mode
@Points("W2E06.1")
public class ItemServiceTest {

    private static final String REST_ITEM_SERVICE = "wad.commonitems.service.RestItemService";

    @Test
    public void restItemServiceExists() {
        Assert.assertNotNull(ReflectionUtils.findClass(REST_ITEM_SERVICE));
    }

    @Test
    public void restItemServiceImplementsItemService() {
        restItemServiceExists();
        Assert.assertTrue(ItemService.class.isAssignableFrom(ReflectionUtils.findClass(REST_ITEM_SERVICE)));
    }

    @Test
    public void restItemServiceHasRestTemplateField() {
        restItemServiceImplementsItemService();

        for (Field field : ReflectionUtils.findClass(REST_ITEM_SERVICE).getDeclaredFields()) {
            if (RestTemplate.class.isAssignableFrom(field.getType())) {
                return;
            }
        }

        Assert.fail();
    }

    @Test
    public void listMethodCorrect() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        restItemServiceHasRestTemplateField();
        String[] result = getRequestInformation(new Slapper() {
            @Override
            public void slap(ItemService service) {
                service.list();
            }
        });

        if (!"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items".equals(result[0])) {
            Assert.fail("The list method of RestItemService should make a query to \"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items\"");
        }

        if (!"GET".equals(result[1].toUpperCase())) {
            Assert.fail("The list method of RestItemService should make a GET query to \"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items\"");
        }
    }

    @Test
    public void postMethodCorrect() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        restItemServiceHasRestTemplateField();
        final Item item = new Item();
        item.setName("popot");
        item.setDescription("kokoa 43");

        String[] result = getRequestInformation(new Slapper() {
            @Override
            public void slap(ItemService service) {
                service.add(item);
            }
        });

        if (!"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items".equals(result[0])) {
            Assert.fail("The add method of RestItemService should make a query to \"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items\"");
        }

        if (!"POST".equals(result[1].toUpperCase())) {
            Assert.fail("The add method of RestItemService should make a POST query to \"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items\"");
        }
    }

    @Test
    public void deleteMethodCorrect() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        restItemServiceHasRestTemplateField();
        String[] result = getRequestInformation(new Slapper() {
            @Override
            public void slap(ItemService service) {
                service.delete(12);
            }
        });

        if (!"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items/12".equals(result[0])) {
            Assert.fail("When deleting an item with id 12, the delete-method of RestItemService should make a query to \"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items/12\"");
        }

        if (!"DELETE".equals(result[1].toUpperCase())) {
            Assert.fail("The delete method of RestItemService should make a DELETE query");
        }
    }

    @Test
    public void deleteMethodCorrectWithAnotherNumber() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String[] result = getRequestInformation(new Slapper() {
            @Override
            public void slap(ItemService service) {
                service.delete(4);
            }
        });

        if (!"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items/4".equals(result[0])) {
            Assert.fail("When deleting an item with id 4, the delete-method of RestItemService should make a query to \"http://t-avihavai.users.cs.helsinki.fi/take-a-rest/app/items/4\"");
        }

        if (!"DELETE".equals(result[1].toUpperCase())) {
            Assert.fail("The delete method of RestItemService should make a DELETE query");
        }
    }

    private String[] getRequestInformation(Slapper slapper) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        ItemService service = (ItemService) ReflectionUtils.findClass(REST_ITEM_SERVICE).newInstance();
        // invoke method annotated with on service as newInstance-call does not do it
        for (Method method : ReflectionUtils.findClass(REST_ITEM_SERVICE).getDeclaredMethods()) {
            method.setAccessible(true);

            if (method.getAnnotation(PostConstruct.class) != null) {
                method.invoke(service);
            }
        }

        RestTemplate template = getRestTemplateFromObject(service);

        final StringBuilder secretPlace = new StringBuilder();
        template.setRequestFactory(new ClientHttpRequestFactory() {
            @Override
            public ClientHttpRequest createRequest(URI uri, HttpMethod hm) throws IOException {
                secretPlace.append(uri).append(" YOLO ").append(hm);
                return null;
            }
        });

        try {
            slapper.slap(service);
        } catch (Exception e) {
        }


        return secretPlace.toString().split(" YOLO ");
    }

    private RestTemplate getRestTemplateFromObject(ItemService itemService) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : ReflectionUtils.findClass(REST_ITEM_SERVICE).getDeclaredFields()) {
            field.setAccessible(true);

            if (RestTemplate.class.isAssignableFrom(field.getType())) {
                return (RestTemplate) field.get(itemService);
            }
        }

        return null;
    }
}
interface Slapper {

    void slap(ItemService service);
}