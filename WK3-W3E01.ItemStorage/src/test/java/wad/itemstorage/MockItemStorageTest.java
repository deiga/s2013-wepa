package wad.itemstorage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@WebAppConfiguration
@Points("W3E01.2")
public class MockItemStorageTest {

    @Resource
    private WebApplicationContext waco;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(waco).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void postingItemReturnsTheSameItemWithId() throws Exception {
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");

        Item postedItem = postItem(item);

        assertTrue(postedItem != null && postedItem.getId() != null);
        assertTrue(item.getName().equals(postedItem.getName()));
        assertTrue(item.getDescription().equals(postedItem.getDescription()));

        deleteItem(postedItem.getId());
    }

    @Test
    public void postAddsItemToList() throws Exception {
        List<Item> items = getItems();

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item = postItem(item);

        List<Item> itemsAfterPost = getItems();

        assertTrue(itemsAfterPost.size() == items.size() + 1);

        deleteItem(item.getId());
    }

    
    @Test
    public void postingTwiceAddsTwoItemsToList() throws Exception {
        List<Item> items = getItems();

        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        
        int idFirst = postItem(item).getId();
        int idSecond = postItem(item).getId();

        List<Item> itemsAfterPost = getItems();

        assertTrue(itemsAfterPost.size() == items.size() + 2);

        deleteItem(idFirst);
        deleteItem(idSecond);
    }
    
    @Test
    public void deleteRemovesAnItem() throws Exception {
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");
        item = postItem(item);

        List<Item> itemsBeforeDelete = getItems();

        deleteItem(item.getId());
        List<Item> itemsAfterDelete = getItems();

        assertTrue(itemsBeforeDelete.size() == itemsAfterDelete.size() + 1);
    }

    @Test
    public void postedItemCanBeRead() throws Exception {
        Item item = new Item();
        item.setName("name");
        item.setDescription("description");

        Item postedItem = postItem(item);

        Item retrieved = getItem(postedItem.getId());

        assertTrue(retrieved != null && retrieved.getId() != null);
        assertTrue(postedItem.getName().equals(retrieved.getName()));
        assertTrue(postedItem.getDescription().equals(retrieved.getDescription()));

        deleteItem(retrieved.getId());
    }

    private List<Item> getItems() throws Exception {
        MvcResult result = mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<List<Item>>() {
        });
    }

    private Item getItem(Integer id) throws Exception {
        MvcResult result = mockMvc.perform(get("/items/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<Item>() {
        });
    }

    private Item postItem(Item item) throws Exception {
        MvcResult result = mockMvc.perform(post("/items").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(item))).andReturn();
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Item>() {
        });
    }

    private void deleteItem(Integer itemId) throws Exception {
        mockMvc.perform(delete("/items/{id}", itemId));
    }
}
