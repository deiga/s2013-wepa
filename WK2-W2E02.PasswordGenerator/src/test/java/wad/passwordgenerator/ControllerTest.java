package wad.passwordgenerator;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.Assert.*;
import org.springframework.web.bind.annotation.RequestMethod;

@Points("W2E02.1")
public class ControllerTest {
    private static final Logger LOG =
            Logger.getLogger(ControllerTest.class.getName());

    private static final String CONTROLLER_CLASS_NAME =
            "wad.passwordgenerator.PasswordGeneratorController";

    @Test
    public void testClassAnnotations() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        classHasOnlyAnnotations(controller, Controller.class);
    }

    @Test
    public void testNewPasswordMethodAnnotations() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        Method m = ReflectionUtils.requireMethod(controller, String.class,
                "newPassword", Model.class);

        methodHasOnlyAnnotations(m, RequestMapping.class);

        RequestMapping requestMapping = m.getAnnotation(RequestMapping.class);
        String[] value = requestMapping.value();
        String pathValue = null;
        if (value != null && value.length > 0) {
            pathValue = value[0];
        }

        assertTrue("The @RequestMapping annotation must listen to the path \"new-password\" " +
                "to catch HTTP-requests for that particular URL-path. Now the path that was listened was: " +
                pathValue, "new-password".equals(pathValue));
        
        RequestMethod[] method = requestMapping.method();
        RequestMethod methodValue = null;
        if(method != null && method.length > 0) {
            methodValue = method[0];
        }
        
        
        assertTrue("The @RequestMapping annotation must accept only GET-methods to the path \"new-password\" " +
                ". Now the annotation had method: " +
                methodValue, methodValue != null && RequestMethod.GET.equals(methodValue));
    }

    @Test
    public void testGeneratePasswordMethod() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        Method m = ReflectionUtils.requireMethod(controller, String.class,
                "generatePassword");

        assertTrue("Verify that method " + m.getName() + " has no annotations. " +
                "Now it has " + m.getAnnotations().length + " annotation(s).",
                m.getAnnotations().length == 0);

        Object controllerInstance;
        try {
            controllerInstance = Reflex.reflect(CONTROLLER_CLASS_NAME).
                    constructor().takingNoParams().invoke();
        } catch (Throwable t) {
            LOG.log(Level.SEVERE, null, t);
            return;
        }

        String password = null;
        try {
            password = (String) m.invoke(controllerInstance);
            UUID.fromString(password);
        } catch (IllegalArgumentException e) {
            fail("Verify that the value of model attribute \"password\" is a valid UUID. Now the value was: " + password);
        } catch (Throwable t) {
            LOG.log(Level.SEVERE, null, t);
            return;
        }
    }

    @Test
    public void testNewPasswordMethod() {
        Class controllerClass;
        Method newPasswordMethod;
        Object controllerInstance;
        
        Model model = new ExtendedModelMap();
        try {
            controllerClass = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
            controllerInstance = Reflex.reflect(CONTROLLER_CLASS_NAME).
                    constructor().takingNoParams().invoke();
            newPasswordMethod = ReflectionUtils.requireMethod(
                    controllerClass, String.class, "newPassword", Model.class);
        } catch (Throwable ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        String result;
        try {
            result = (String) newPasswordMethod.invoke(controllerInstance, model);
        } catch (IllegalAccessException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        } catch (IllegalArgumentException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        } catch (InvocationTargetException ex) {
            LOG.log(Level.SEVERE, null, ex);
            return;
        }

        Map<String, Object> modelMap = model.asMap();
        assertTrue("Verify that you add a model attribute called \"password\" in the " + newPasswordMethod.getName() + "-method of " +
                controllerClass.getSimpleName(), modelMap.containsKey("password"));
        Class valueClass = modelMap.get("password").getClass();
        assertTrue("Verify that you the model attribute called \"password\" is a string. Now the type of the attribute was: " +
                valueClass.getName(), String.class.equals(valueClass));

        String password = (String) modelMap.get("password");
        try {
            UUID.fromString(password);
        } catch (IllegalArgumentException e) {
            fail("Verify that the value of model attribute \"password\" is a valid UUID. Now the value was: " + password);
        }

        assertTrue("The method " + newPasswordMethod.getName() +
                " should return a string that contains the view name," +
                " which is \"password\" in this exercise", "password".equals(result));
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
