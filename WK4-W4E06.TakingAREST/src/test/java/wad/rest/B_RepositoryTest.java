package wad.rest;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import static org.junit.Assert.*;
import org.junit.Test;
import org.springframework.data.repository.CrudRepository;

@Points("W4E06.2")
public class B_RepositoryTest {

    private final String SLEEP_CLASSNAME = "wad.rest.domain.Sleep";
    private final String SLEEP_REPOSITORY_CLASSNAME = "wad.rest.repository.SleepRepository";

    @Test
    public void exists() {
        Reflex.reflect(SLEEP_REPOSITORY_CLASSNAME);
    }

    @Test
    public void isAnInterface() {
        
        assertTrue("Class " + SLEEP_REPOSITORY_CLASSNAME + " should be an interface.", Modifier.isInterface(Reflex.reflect(SLEEP_REPOSITORY_CLASSNAME).cls().getModifiers()));
    }

    @Test
    public void extendsCrudRepository() {
        
        assertTrue("Class " + SLEEP_REPOSITORY_CLASSNAME + " should extend interface " + CrudRepository.class.getName() + ".", CrudRepository.class.isAssignableFrom(Reflex.reflect(SLEEP_REPOSITORY_CLASSNAME).cls()));
    }

    @Test
    public void extendsCrudRepositoryWithCorrectTypeParameters() {
        
        extendsCrudRepositoryWithCorrectTypeParameters(SLEEP_REPOSITORY_CLASSNAME, SLEEP_CLASSNAME);
    }

    private void extendsCrudRepositoryWithCorrectTypeParameters(String clazzName, String typeOneClass) {
        
        Class clazz = Reflex.reflect(clazzName).cls();
        
        Type[] typeArguments = null;
        try {
            Type[] types = clazz.getGenericInterfaces();
            ParameterizedType type = (ParameterizedType) types[0];
            typeArguments = type.getActualTypeArguments();
        } catch (Exception e) {
            fail("When class " + clazzName + " extends CrudRepository, it should provide classes " + typeOneClass + " and " + Long.class.getName() + " as type parameters.");
        }

        assertTrue("When class " + clazzName + " extends CrudRepository, it should provide classes " + typeOneClass + " and " + Long.class.getName() + " as type parameters.", typeArguments[0].equals(Reflex.reflect(typeOneClass).cls()));
        assertTrue("When class " + clazzName + " extends CrudRepository, it should provide classes " + typeOneClass + " and " + Long.class.getName() + " as type parameters.", typeArguments[1].equals(Long.class));
    }
}