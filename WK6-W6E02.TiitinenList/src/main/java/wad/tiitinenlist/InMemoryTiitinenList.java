package wad.tiitinenlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InMemoryTiitinenList implements TiitinenListService {

    private Map<Long, ListItem> items = new HashMap<Long, ListItem>();
    private Long counter = 0L;
    
    @PostConstruct
    private void init() {
        create("041033421528;12;00;50;;ADAM, EWALD:;;;16560,00");
        create("040164334619;12;00;50;;ADAM, FRANK:;;;17105,00");
        create("042163561234;12;00;50;;ARTO, EWALD:;;;36105,00");
    }

    @Override
    public ListItem create(String content) {
        counter++;

        ListItem item = new ListItem();
        item.setId(counter);
        item.setContent(content);
        items.put(item.getId(), item);

        return item;
    }

    @Override
    public void remove(Long identifier) {
        items.remove(identifier);
    }

    @Override
    public List<ListItem> list() {
        return new ArrayList<ListItem>(items.values());
    }
}
