package wad.rest;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static org.junit.Assert.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public abstract class TestHelper {

    protected void classHasOnlyAnnotations(Class clazz, Class... annotations) {
        for (Class annotation : annotations) {
            if (clazz.getAnnotation(annotation) == null) {
                fail("Class " + clazz.getName() + " is missing annotation " + annotation.getCanonicalName());
            }
        }

        if (clazz.getAnnotations().length != annotations.length) {
            fail("Verify that class " + clazz.getName() + " has all the required annotations and nothing more.");
        }
    }

    protected void methodHasOnlyAnnotations(Method method, Class... annotations) {
        if (annotations == null || annotations.length == 0) {
            if (method.getAnnotations().length > 0) {
                fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " should not have annotations");
            }
        }

        for (Class annotation : annotations) {
            if (method.getAnnotation(annotation) == null) {
                fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " is missing annotation " + annotation.getCanonicalName());
            }
        }

        if (method.getAnnotations().length != annotations.length) {
            fail("Verify that method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " has all the required annotations and nothing more.");
        }
    }

    protected void methodHasTransactionalDefinedWith(Method method, boolean readOnly) {
        Transactional transactional = method.getAnnotation(Transactional.class);
        if (transactional == null) {
            fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " is missing annotation " + Transactional.class.getCanonicalName());
        }

        if (transactional.readOnly() != readOnly) {
            fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " has incorrect value for readOnly parameter in Transactional annotation");
        }
    }

    protected void methodHasRequestMappingDefinedWith(Method method, String value, RequestMethod requestMethod) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (requestMapping == null) {
            fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " is missing annotation " + RequestMapping.class.getCanonicalName());
        }

        String[] values = requestMapping.value();
        if (values == null || values.length == 0) {
            fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " is missing parameter \"value\" for annotation " + RequestMapping.class.getCanonicalName());
        }

        assertEquals("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " has incorrect value for parameter \"value\" in RequestMapping annotation",
            requestMapping.value()[0], value);

        RequestMethod[] methods = requestMapping.method();
        if (methods == null || methods.length == 0) {
            fail("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " is missing parameter \"method\" for annotation " + RequestMapping.class.getCanonicalName());
        }
        assertEquals("Method " + method.getName() + " in class " + method.getDeclaringClass().getName() + " has incorrect value for parameter \"method\" in RequestMapping annotation",
            requestMapping.method()[0], requestMethod);
    }

    protected void fieldIsPrivate(Field field) {
        assertTrue("Verify that field " + field.getName() + " in class " + field.getDeclaringClass().getName() + " is private" ,
                Modifier.isPrivate(field.getModifiers()));
    }

    protected void fieldHasOnlyAnnotations(Field field, Class... annotations) {
        for (Class annotation : annotations) {
            if (field.getAnnotation(annotation) == null) {
                fail("Field " + field.getName() + " in class " + field.getDeclaringClass().getName() + " is missing annotation " + annotation.getCanonicalName());
            }
        }

        if (field.getAnnotations().length != annotations.length) {
            fail("Verify that field " + field.getName() + " in class " + field.getDeclaringClass().getName() + " has all the required annotations and nothing more.");
        }
    }
}
