package wad.lovemeter.servlet;

import fi.helsinki.cs.tmc.edutestutils.Points;
import fi.helsinki.cs.tmc.edutestutils.ReflectionUtils;
import fi.helsinki.cs.tmc.edutestutils.Reflex;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.junit.Assert.*;
import org.junit.Test;

import static org.mockito.Mockito.*;
import wad.lovemeter.service.LoveMeterService;

@Points("W1E07.2")
public class LoveServletTest {

    private static final String SERVLET_CLASSNAME = "wad.lovemeter.servlet.LoveServlet";
    private Class loveServlet;

    @Test
    public void classLoveServletExists() {
        loveServlet = ReflectionUtils.findClass(SERVLET_CLASSNAME);
    }

    @Test
    public void extendsHttpServlet() {
        classLoveServletExists();
        assertTrue("Class LoveServlet must extends javax.servlet.http.HttpServlet", HttpServlet.class.isAssignableFrom(loveServlet));
    }

    @Test
    public void hasNoStaticFields() {
        extendsHttpServlet();

        for (Field f : loveServlet.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers())) {
                fail("There should be no static fields in the LoveServlet class. " + f.getName() + " was static.");
            }

        }
    }

    @Test
    public void usesLoveMeterServiceInterfaceAsField() {
        hasNoStaticFields();

        boolean found = false;
        for (Field f : loveServlet.getDeclaredFields()) {
            if (f.getType().equals(LoveMeterService.class)) {
                found = true;
            }

            if (f.getType().toString().contains("Kumpula")) {
                fail("You should not use class KumpulaLoveMeter as a field in LoveServlet. Program to interfaces instead: use LoveMeterService as field type.");
            }
        }

        if (!found) {
            fail("Verify that you use the LoveMeterService as a field in LoveServlet-class.");
        }
    }

    @Test
    public void overridesDoGetMethod() {
        usesLoveMeterServiceInterfaceAsField();

        boolean found = false;
        for (Method m : loveServlet.getDeclaredMethods()) {
            if (m.getName().contains("doGet")) {
                found = true;
            }
        }

        if (!found) {
            fail("Verify that you override doGet-method from HttpServlet in LoveServlet-class.");
        }
    }

    @Test
    public void assignsNamesAndMatchToRequest() throws Throwable {
        usesLoveMeterServiceInterfaceAsField();

        boolean found = false;
        for (Method m : loveServlet.getDeclaredMethods()) {
            if (m.getName().contains("doGet")) {
                found = true;
            }
        }

        if (!found) {
            fail("Verify that you override doGet-method from HttpServlet in LoveServlet-class.");
        }


        HttpServlet loveServletInstance = (HttpServlet) Reflex.reflect(SERVLET_CLASSNAME).constructor().takingNoParams().invoke();

        String name1 = UUID.randomUUID().toString();
        String name2 = UUID.randomUUID().toString();


        LoveMeterService lms = (LoveMeterService) Reflex.reflect("wad.lovemeter.service.KumpulaLoveMeter").constructor().takingNoParams().invoke();
        int match = lms.match(name1, name2);


        // luodaan mock-objektit
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // kerrotaan mitä tapahtuu kun metodeja kutsutaan tietyillä parametreilla
        when(request.getParameter(eq("name1"))).thenReturn(name1);
        when(request.getParameter(eq("name2"))).thenReturn(name2);
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher(eq("/WEB-INF/jsp/love.jsp"))).thenReturn(dispatcher);

        // tehdään oikea kutsu
        loveServletInstance.service(request, response);

        // varmistetaan että oikeita asioita kutsuttiin
        verify(request).getParameter("name1");
        verify(request).getParameter("name2");

        verify(request).setAttribute("name1", name1);
        verify(request).setAttribute("name2", name2);

        verify(request).setAttribute("match", match);
        verify(request).getRequestDispatcher("/WEB-INF/jsp/love.jsp");

        verify(dispatcher).forward(request, response);
    }
    
    
}
