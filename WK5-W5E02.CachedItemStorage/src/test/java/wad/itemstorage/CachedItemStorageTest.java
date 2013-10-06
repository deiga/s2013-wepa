package wad.itemstorage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@Points("W5E02")
public class CachedItemStorageTest {

    @Test
    public void cacheablesPresent() throws Exception {
        assertNotNull("Method getItems should have the correct Cacheable-annotation (verify also that a correct cache is used.)", ItemStorageController.class.getDeclaredMethod("getItems").getAnnotation(Cacheable.class));
        assertNotNull("Method getItem should have the correct Cacheable-annotation (verify also that a correct cache is used.)", ItemStorageController.class.getDeclaredMethod("getItem", Long.class).getAnnotation(Cacheable.class));
    }

    @Test
    public void cacheEvictsPresent() throws Exception {
        assertNotNull("Method postItem should have the annotation for clearing the cache.", ItemStorageController.class.getDeclaredMethod("postItem", Item.class).getAnnotation(CacheEvict.class));
        assertNotNull("Method deleteItem should have the annotation for clearing the cache.", ItemStorageController.class.getDeclaredMethod("deleteItem", Long.class).getAnnotation(CacheEvict.class));
    }
}