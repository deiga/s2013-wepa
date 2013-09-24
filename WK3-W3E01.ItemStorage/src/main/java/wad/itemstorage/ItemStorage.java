package wad.itemstorage;

// DO NOT CHANGE THIS INTERFACE
import java.util.List;

public interface ItemStorage {
    Item create(Item item);
    Item read(Integer id);
    Item update(Integer id, Item item);
    Item delete(Integer id);

    List<Item> list();
}
