package wad.storage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.junit.Test;

@Points("W3E04.1")
public class ItemTest {

    private final String ITEM_CLASSNAME = "wad.storage.domain.Item";
    private final Map<String, Class> attributeAndType = new TreeMap<String, Class>() {
        {
            put("id", Long.class);
            put("name", String.class);
            put("count", Integer.class);
        }
    };
    private final Map<String, List<Class>> requiredAnnotations = new TreeMap<String, List<Class>>() {
        {
            put("id", EntityTester.createAnnotationList(Id.class, GeneratedValue.class, Column.class));
            put("name", EntityTester.createAnnotationList(Column.class));
            put("count", EntityTester.createAnnotationList(Column.class));
        }
    };
    
    @Test
    public void validateItem() {
        new EntityTester().testEntity(ITEM_CLASSNAME, attributeAndType, requiredAnnotations);
    }
}