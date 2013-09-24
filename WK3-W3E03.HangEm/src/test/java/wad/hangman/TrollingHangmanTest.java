package wad.hangman;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("W3E03.2")
public class TrollingHangmanTest {
    private Method getWordOptionsMethod = null;

    @Test
    public void interfaceHasBeenModified() throws Exception {
        for (Method method : WordService.class.getDeclaredMethods()) {
            if (method.getName().equals("getWordOptions")) {
                method.setAccessible(true);
                getWordOptionsMethod = method;
                break;
            }
        }

        assertTrue("Interface WordService should have a method called getWordOptions, which takes a List and a String as parameters.",
                getWordOptionsMethod != null && getWordOptionsMethod.getParameterTypes().length == 2
                && List.class.equals(getWordOptionsMethod.getParameterTypes()[0]) && String.class.equals(getWordOptionsMethod.getParameterTypes()[1]));


        assertTrue("Method getWordOptions in Interface WordService should return a List of Strings.", List.class.equals(getWordOptionsMethod.getReturnType()));
    }

    @Test
    public void inMemoryWordServiceImplementsInterface() throws Exception {
        assertTrue("InMemoryWordService should implement the interface WordService.", WordService.class.isAssignableFrom(InMemoryWordService.class));
    }

    @Test
    public void getWordOptionsWorksAsIntended() throws Throwable {
        interfaceHasBeenModified();
        WordService service = new InMemoryWordService();
        List<String> mustHave = Arrays.asList("a", "e", "i", "u");
        List<String> remaining = (List<String>) ReflectionUtils.invokeMethod(
                List.class,
                getWordOptionsMethod,
                service, mustHave, "1");

        assertTrue("When the words with wovels a, e, i, and u are taken out, there should be only one word remaining in the word list.", remaining.size() == 1);

        remaining = (List<String>) ReflectionUtils.invokeMethod(
                List.class,
                getWordOptionsMethod,
                service, mustHave, "o");
        assertTrue("When the words with wovels a, e, i, and u are taken out, and the optional character is o, the only possible word should be \"tomorrow\".", remaining.size() == 1 && remaining.get(0).equals("tomorrow"));
    }
}
