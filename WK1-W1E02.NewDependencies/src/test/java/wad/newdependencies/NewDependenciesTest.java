package wad.newdependencies;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import static org.junit.Assert.*;
import org.junit.Test;

@Points("W1E02")
public class NewDependenciesTest {

    @Test
    public void dependenciesAdded() {
        Class clazz = null;
        try {
            clazz = ReflectionUtils.findClass("org.slf4j.LoggerFactory");
        } catch (Throwable t) {
            fail("Dependency not found, verify that you have added the required dependencies. Error: " + t.getMessage());
        }
    }
}
