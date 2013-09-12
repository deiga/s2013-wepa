package wad.commonitems.service;

import java.util.List;
import wad.commonitems.domain.Item;

public interface ItemService {
    void add(Item item);
    void delete(Integer id);
    List<Item> list();
}
