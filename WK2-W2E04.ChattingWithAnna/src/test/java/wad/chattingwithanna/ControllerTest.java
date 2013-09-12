package wad.chattingwithanna;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Method;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.chattingwithanna.controller.ChatControllerInterface;

import static org.junit.Assert.*;

@Points("W2E04.1")
public class ControllerTest {
    private static final Logger LOG =
            Logger.getLogger(ControllerTest.class.getName());

    private static final String CONTROLLER_CLASS_NAME =
            "wad.chattingwithanna.controller.ChatController";

    @Test
    public void testClassInterface() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        assertTrue("Class " + CONTROLLER_CLASS_NAME + " must implement interface: "  +
                ChatControllerInterface.class.getName(),
                ChatControllerInterface.class.isAssignableFrom(controller));
    }

    @Test
    public void testClassAnnotations() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        classHasOnlyAnnotations(controller, Controller.class);
    }

    @Test
    public void testAddMessageMethodAnnotations() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        Method m = ReflectionUtils.requireMethod(controller, String.class,
                "addMessage", String.class);

        methodHasOnlyAnnotations(m, RequestMapping.class);

        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
        String[] value = requestMapping.value();
        String pathValue = null;
        if (value != null && value.length > 0) {
            pathValue = value[0];
        }

        assertTrue("The @RequestMapping annotation for method " + m.getName() +
                " must have parameter \"add-message\" " + "to catch HTTP-requests" +
                " for that particular URL-path. Now the annotation had parameter value: " +
                pathValue, "add-message".equals(pathValue));

        RequestMethod[] requestMethods = requestMapping.method();
        assertTrue("The @RequestMapping annotation for method " + m.getName() +
                " must have exactly one RequestMethod definition. " +
                "Now the request method was not explicitly defined.",
                requestMethods != null);
        assertTrue("The @RequestMapping annotation for method " + m.getName() +
                " must have exactly one RequestMethod definition. Now it had: " +
                requestMethods.length, requestMethods.length == 1);
        assertTrue("The @RequestMapping annotation for method " + m.getName() +
                " must have RequestMethod.POST as request method. Now it was: " +
                requestMethods[0], RequestMethod.POST.equals(requestMethods[0]));
    }

    @Test
    public void testListMethodAnnotations() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        Method m = ReflectionUtils.requireMethod(controller, String.class,
                "list", Model.class);

        methodHasOnlyAnnotations(m, RequestMapping.class);

        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
        String[] value = requestMapping.value();
        String pathValue = null;
        if (value != null && value.length > 0) {
            pathValue = value[0];
        }

        assertTrue("The @RequestMapping annotation for method " + m.getName() +
                " must have parameter \"list\" " + "to catch HTTP-requests" +
                " for that particular URL-path. Now the annotation had parameter value: " +
                pathValue, "list".equals(pathValue));

//        RequestMethod[] requestMethods = requestMapping.method();
//        assertTrue("The @RequestMapping annotation for method " + m.getName() +
//                " must have exactly one RequestMethod definition. " +
//                "Now the request method was not explicitly defined.",
//                requestMethods != null);
//        assertTrue("The @RequestMapping annotation for method " + m.getName() +
//                " must have exactly one RequestMethod definition. Now it had: " +
//                requestMethods.length, requestMethods.length == 1);
//        assertTrue("The @RequestMapping annotation for method " + m.getName() +
//                " must have RequestMethod.GET as request method. Now it was: " +
//                requestMethods[0], RequestMethod.GET.equals(requestMethods[0]));
    }

    private void classHasOnlyAnnotations(Class clazz, Class... annotations) {
        for (Class annotation : annotations) {
            if (clazz.getAnnotation(annotation) == null) {
                fail("Class " + clazz.getName() + " is missing annotation " + annotation.getCanonicalName());
            }
        }

        if (clazz.getAnnotations().length != annotations.length) {
            fail("Verify that class " + clazz.getName() + " has all the required annotations and nothing more.");
        }
    }

    private void methodHasOnlyAnnotations(Method method, Class... annotations) {
        for (Class annotation : annotations) {
            if (method.getAnnotation(annotation) == null) {
                fail("Method " + method.getName() + " is missing annotation " + annotation.getCanonicalName());
            }
        }

        if (method.getAnnotations().length != annotations.length) {
            fail("Verify that method " + method.getName() + " has all the required annotations and nothing more.");
        }
    }
}
