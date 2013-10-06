package wad.itemstorage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemStorageController {

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value = "items", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable("items")
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @RequestMapping(value = "items/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Cacheable("items")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findOne(id);
    }

    @RequestMapping(value = "items", method = RequestMethod.POST)
    @ResponseBody
    @CacheEvict(value = "items", allEntries = true)
    public Item postItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    @RequestMapping(value = "items/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @CacheEvict(value = "items")
    public Item deleteItem(@PathVariable Long id) {
        Item item = itemRepository.findOne(id);
        itemRepository.delete(id);
        return item;
    }
}
