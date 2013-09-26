package wad.registration;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.UUID;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import wad.registration.controller.RegistrationController;
import wad.registration.domain.Registration;
import wad.registration.service.InMemoryRegistrationService;
import wad.registration.service.RegistrationService;

@Points("W4E04")
public class GoingForABetterBallTest {

    @Test
    public void successReturnedOnGetToSuccess() throws Exception {
        createControllerWithViewResolver()
                .perform(get("/success"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/success.jsp"));
    }

    @Test
    public void formReturnedOnGetToRegister() throws Exception {
        createControllerWithViewResolver()
                .perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
    }

    @Test
    public void redirectToSuccessOnSuccessfulPostToRegister() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString().substring(0, 10);
        String address = UUID.randomUUID().toString().substring(0, 16);
        String email = name.substring(0, 4) + "@" + address.substring(0, 4) + ".fi";

        createControllerWithViewResolver(registrationService)
                .perform(post("/register")
                .param("name", name)
                .param("address", address)
                .param("email", email))
                .andExpect(redirectedUrl("success"));


        Registration registration = new Registration(name, address, email);
        assertTrue("The registration should be added to the registrationService on success.",
                registrationService.hasRegistration(registration));
    }

    @Test
    public void returnToFormOnTooShortName() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString().substring(0, 3);
        String address = UUID.randomUUID().toString().substring(0, 16);
        String email = name.substring(0, 1) + "@" + address.substring(0, 4) + ".fi";

        try {
            createControllerWithViewResolver(registrationService)
                    .perform(post("/register")
                    .param("name", name)
                    .param("address", address)
                    .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the name is too short, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }

        Registration registration = new Registration(name, address, email);
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(registration));
    }

    @Test
    public void returnToFormOnTooLongName() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();
        name = name.substring(0, 31);
        String address = UUID.randomUUID().toString().substring(0, 16);
        String email = name.substring(0, 1) + "@" + address.substring(0, 4) + ".fi";

        try {
            createControllerWithViewResolver(registrationService)
                    .perform(post("/register")
                    .param("name", name)
                    .param("address", address)
                    .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the name is too long, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }



        Registration registration = new Registration(name, address, email);
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(registration));
    }

    @Test
    public void returnToFormOnTooShortAddress() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 3);
        String email = name.substring(0, 4) + "@" + address.substring(0, 1) + ".fi";

        try {
            createControllerWithViewResolver(registrationService)
                    .perform(post("/register")
                    .param("name", name)
                    .param("address", address)
                    .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the address is too short, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }


        Registration registration = new Registration(name, address, email);
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(registration));
    }

    @Test
    public void returnToFormOnTooLongAddress() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();
        address = address.substring(0, 51);
        String email = name.substring(0, 4) + "@" + address.substring(0, 1) + ".fi";

        try {
            createControllerWithViewResolver(registrationService)
                    .perform(post("/register")
                    .param("name", name)
                    .param("address", address)
                    .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the address is too long, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }


        Registration registration = new Registration(name, address, email);
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(registration));
    }

    @Test
    public void returnToFormOnEmail() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 16);
        String email = name.substring(0, 3) + address.substring(0, 5) + ".fi";

        try {
            createControllerWithViewResolver(registrationService)
                    .perform(post("/register")
                    .param("name", name)
                    .param("address", address)
                    .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the email is invalid, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }

        Registration registration = new Registration(name, address, email);
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(registration));
    }

    @Test
    public void returnToFormOnAllFailure() throws Exception {
        InMemoryRegistrationService registrationService = new InMemoryRegistrationService();

        String name = UUID.randomUUID().toString().substring(0, 2);
        String address = UUID.randomUUID().toString().substring(0, 2);
        String email = name.substring(0, 1) + address.substring(0, 1) + ".fi";

        try {
            createControllerWithViewResolver(registrationService)
                    .perform(post("/register")
                    .param("name", name)
                    .param("address", address)
                    .param("email", email))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When any of the inputs is faulty, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }

        Registration registration = new Registration(name, address, email);
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(registration));
    }

    private MockMvc createControllerWithViewResolver() {
        return createControllerWithViewResolver(null);
    }

    private MockMvc createControllerWithViewResolver(RegistrationService registrationService) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        RegistrationController registrationController = new RegistrationController();
        if (registrationService != null) {
            registrationController.setRegistrationService(registrationService);
        }

        return standaloneSetup(registrationController)
                .setViewResolvers(viewResolver).build();
    }
}
