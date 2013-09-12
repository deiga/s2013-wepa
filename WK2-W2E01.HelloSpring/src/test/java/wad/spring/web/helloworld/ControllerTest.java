package wad.spring.web.helloworld;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

@Points("W2E01.1")
public class ControllerTest {

    private static final String SPRING_CONTROLLER_ANNOTATION =
            "org.springframework.stereotype.Controller";
    private static final String SPRING_REQUEST_MAPPING_ANNOTATION =
            "org.springframework.web.bind.annotation.RequestMapping";
    private static final String CONTROLLER_CLASS_NAME =
            "wad.spring.web.helloworld.HelloWorldController";
    private static final String MODEL_INTERFACE_NAME =
            "org.springframework.ui.Model";    
        private static final String EXTENDEDMODELMAP_CLASS_NAME =
            "org.springframework.ui.ExtendedModelMap";

    @Test
    public void testClass() {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);

        Class controllerAnnotation = ReflectionUtils.findClass(
                SPRING_CONTROLLER_ANNOTATION);
        classHasOnlyAnnotations(controller, controllerAnnotation);
    }

    @Test
    public void testSayHelloMethod() throws Exception {
        Class controller = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        Method m = ReflectionUtils.requireMethod(controller, String.class, "sayHello",
                ReflectionUtils.findClass(MODEL_INTERFACE_NAME));

        Class requestMappingAnnotation = ReflectionUtils.findClass(
                SPRING_REQUEST_MAPPING_ANNOTATION);
        methodHasOnlyAnnotations(m, requestMappingAnnotation);

        Annotation annotationInstance = m.getAnnotation(requestMappingAnnotation);
        Method valueMethod = annotationInstance.annotationType().getDeclaredMethod("value");
        Object value = valueMethod.invoke(annotationInstance);
        String pathValue = null;
        if (value instanceof String[]) {
            if (((String[]) value).length > 0) {
                pathValue = ((String[]) value)[0];
            }
        } else if (value instanceof String) {
            pathValue = (String) value;
        }

        assertTrue("The @RequestMapping annotation must have value parameter \"hello\" to catch requests for the path \"hello\". Now the annotation had parameter value: "
                + pathValue, "hello".equals(pathValue));
        
        Method methodMethod = annotationInstance.annotationType().getDeclaredMethod("method");
        Object method = methodMethod.invoke(annotationInstance);
        String pathMethod = null;
        if (method instanceof Object[]) {
            if (((Object[]) method).length > 0) {
                pathMethod = "" + ((Object[]) method)[0];
            }
        } else if (method instanceof Object) {
            pathMethod = "" + method;
        }

        assertTrue("The @RequestMapping annotation must have method \"RequestMethod.GET\" to catch GET-type requests. Now the annotation had parameter method: "
                + pathMethod, "GET".equals(pathMethod));
    }

    @Test
    public void setsRequestAttributeAndReturnsViewName() throws Throwable {
        Class controllerClass = ReflectionUtils.findClass(CONTROLLER_CLASS_NAME);
        Method sayHelloMethod = ReflectionUtils.requireMethod(
                controllerClass, String.class, "sayHello",
                ReflectionUtils.findClass(MODEL_INTERFACE_NAME));

        Object controllerInstance = Reflex.reflect(CONTROLLER_CLASS_NAME).
                constructor().takingNoParams().invoke();

        // luodaan mock-objekti
        Object model = createModel();
        
        // tehdään oikea kutsu
        String result = (String) sayHelloMethod.invoke(
                controllerInstance, model);

        assertTrue("The method " + sayHelloMethod.getName()
                + " should return a string that contains the view name,"
                + " which is \"hello\" in this exercise", "hello".equals(result));

        // varmistetaan että modeliin asetettiin oikea asia
        Map<String, Object> modelData = (Map<String, Object>) ReflectionUtils.requireMethod(ReflectionUtils.findClass("org.springframework.ui.ExtendedModelMap"), "asMap").invoke(model);
        
        assertTrue(modelData != null && modelData.containsKey("message") && modelData.get("message").equals("Great Scott!"));
        
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

    private Object createModel() throws Throwable {
        Class modelClazz = ReflectionUtils.findClass(EXTENDEDMODELMAP_CLASS_NAME);
        return ReflectionUtils.invokeConstructor(modelClazz.getConstructor());
    }
}
