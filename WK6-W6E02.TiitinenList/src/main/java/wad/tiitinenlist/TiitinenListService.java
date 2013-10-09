package wad.tiitinenlist;

import java.util.List;

public interface TiitinenListService {
    ListItem create(String content);
    void remove(Long identifier);
    List<ListItem> list();
}
