package wad.storage;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.data.repository.CrudRepository;

@Points("W3E04.2")
public class RepositoryTest {

    private final String ITEM_REPOSITORY_CLASSNAME = "wad.storage.repository.ItemRepository";
    private final String ITEM_CLASSNAME = "wad.storage.domain.Item";

    @Test
    public void exists() {
        Reflex.reflect(ITEM_REPOSITORY_CLASSNAME);
    }

    @Test
    public void isAnInterface() {
        exists();
        Class clazz = Reflex.reflect(ITEM_REPOSITORY_CLASSNAME).cls();
        assertTrue("Class " + ITEM_REPOSITORY_CLASSNAME + " should be an interface.", Modifier.isInterface(clazz.getModifiers()));
    }

    @Test
    public void extendsCrudRepository() {
        isAnInterface();
        Class clazz = Reflex.reflect(ITEM_REPOSITORY_CLASSNAME).cls();
        assertTrue("Class " + ITEM_REPOSITORY_CLASSNAME + " should extend interface " + CrudRepository.class.getName() + ".", CrudRepository.class.isAssignableFrom(clazz));
    }

    @Test
    public void extendsCrudRepositoryWithCorrectTypeParameters() {
        extendsCrudRepository();
        Class clazz = Reflex.reflect(ITEM_REPOSITORY_CLASSNAME).cls();
        Type[] typeArguments = null;
        try {
            Type[] types = clazz.getGenericInterfaces();
            ParameterizedType type = (ParameterizedType) types[0];
            typeArguments = type.getActualTypeArguments();
        } catch (Exception e) {
            fail("When class " + ITEM_REPOSITORY_CLASSNAME + " extends CrudRepository, it should provide classes " + ITEM_CLASSNAME + " and " + Long.class.getName() + " as type parameters.");
        }

        assertTrue("When class " + ITEM_REPOSITORY_CLASSNAME + " extends CrudRepository, it should provide classes " + ITEM_CLASSNAME + " and " + Long.class.getName() + " as type parameters.", typeArguments[0].equals(Reflex.reflect("wad.storage.domain.Item").cls()));
        assertTrue("When class " + ITEM_REPOSITORY_CLASSNAME + " extends CrudRepository, it should provide classes " + ITEM_CLASSNAME + " and " + Long.class.getName() + " as type parameters.", typeArguments[1].equals(Long.class));
    }
}
