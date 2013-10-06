package wad.familyalbum;

import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.junit.Assert.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class TestHelper {
    protected static final String JPA_SNIPPET_CLASS_NAME =
            "wad.pastebin.data.JpaSnippet";
    protected static final String JPA_SNIPPET_REPOSITORY_CLASS_NAME =
            "wad.pastebin.repository.JpaSnippetRepository";
    protected static final String JPA_SNIPPET_SERVICE_CLASS_NAME =
            "wad.pastebin.service.JpaSnippetService";

    protected static final String JPA_USER_CLASS_NAME =
            "wad.pastebin.data.JpaUser";
    protected static final String JPA_USER_REPOSITORY_CLASS_NAME =
            "wad.pastebin.repository.JpaUserRepository";
    protected static final String JPA_USER_SERVICE_CLASS_NAME =
            "wad.pastebin.service.JpaUserService";

    protected void testIfClassExists(String name, Class[] interfaces,
            Class[] annotations) {
        Class clazz = ReflectionUtils.findClass(name);
        for (Class iface : interfaces) {
            assertTrue("Class " + name + " must implement interface: "  +
                    iface.getName(),
                    iface.isAssignableFrom(clazz));
        }
        classHasOnlyAnnotations(clazz, annotations);
    }

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
