/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.itemstorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author timosand
 */
@Service
public class InMemoryItemStorage implements ItemStorage {
    
    Map<Integer, Item> itemStorage;
    private static Integer ID = 0;

    public InMemoryItemStorage() {
        this.itemStorage = new HashMap<Integer, Item>();
    }

    @Override
    public Item create(Item item) {
        item.setId(ID++);
        itemStorage.put(item.getId(), item);
        return item;
    }

    @Override
    public Item read(Integer id) {
        return itemStorage.get(id);
    }

    @Override
    public Item update(Integer id, Item item) {
        Item update = itemStorage.get(id);
        if (update == null) return null;
        update.setDescription(item.getDescription());
        update.setName(item.getName());
        return update;
    }

    @Override
    public Item delete(Integer id) {
        return itemStorage.remove(id);
    }

    @Override
    public List<Item> list() {
        return new ArrayList(itemStorage.values());
    }
    
}
