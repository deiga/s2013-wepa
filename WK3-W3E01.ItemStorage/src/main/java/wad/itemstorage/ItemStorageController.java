/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.itemstorage;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author timosand
 */
@Controller
public class ItemStorageController {
    
    @Autowired
    ItemStorage itemStorage;
    
    @RequestMapping(value = "items", method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItems(Model model) {
        return itemStorage.list();
    }
    
    @RequestMapping(value = "items/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Item getItem(@PathVariable Integer id) {
        return itemStorage.read(id);
    }
    
    @RequestMapping(value = "items/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Item updateItem(@PathVariable Integer id, @RequestBody Item item) {
        return itemStorage.update(id, item);
    }
    
    @RequestMapping(value = "items/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Item deleteItem(@PathVariable Integer id) {
        return itemStorage.delete(id);
    }
    
    @RequestMapping(value = "items", method = RequestMethod.POST)
    @ResponseBody
    public Item getItems(@RequestBody Item item) {
        return itemStorage.create(item);
    }
    
}
