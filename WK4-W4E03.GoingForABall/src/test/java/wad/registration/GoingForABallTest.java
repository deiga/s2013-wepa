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
import wad.registration.service.InMemoryRegistrationService;
import wad.registration.service.RegistrationService;

@Points("W4E03")
public class GoingForABallTest {

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

        assertTrue("The registration should be added to the registrationService on success.",
                registrationService.hasRegistration(name, address, email));
    }

    @Test
    public void returnToFormOnTooShortNameFailure() throws Exception {
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
                    .andExpect(model().attribute("name", name))
                    .andExpect(model().attribute("address", address))
                    .andExpect(model().attribute("email", email))
                    .andExpect(model().attributeExists("nameError"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When form has been populated with data where the name is too short, the user must be returned to the form with previously submitted data and a error message related to the name. Error: " + t.getMessage());
        }

        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(name, address, email));
    }

    @Test
    public void returnToFormOnTooLongNameFailure() throws Exception {
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
                    .andExpect(model().attribute("name", name))
                    .andExpect(model().attribute("address", address))
                    .andExpect(model().attribute("email", email))
                    .andExpect(model().attributeExists("nameError"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When form has been populated with data where the name is too long, the user must be returned to the form with previously submitted data and a error message related to the name. Error: " + t.getMessage());
        }
        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(name, address, email));
    }

    @Test
    public void returnToFormOnTooShortAddressFailure() throws Exception {
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
                    .andExpect(model().attribute("name", name))
                    .andExpect(model().attribute("address", address))
                    .andExpect(model().attribute("email", email))
                    .andExpect(model().attributeExists("addressError"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When form has been populated with data where the address is too short, the user must be returned to the form with previously submitted data and a error message related to the address. Error: " + t.getMessage());
        }

        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(name, address, email));
    }

    @Test
    public void returnToFormOnTooLongAddressFailure() throws Exception {
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
                    .andExpect(model().attribute("name", name))
                    .andExpect(model().attribute("address", address))
                    .andExpect(model().attribute("email", email))
                    .andExpect(model().attributeExists("addressError"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When form has been populated with data where the address is too long, the user must be returned to the form with previously submitted data and a error message related to the address. Error: " + t.getMessage());
        }


        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(name, address, email));
    }

    @Test
    public void returnToFormOnEmailFailure() throws Exception {
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
                    .andExpect(model().attribute("name", name))
                    .andExpect(model().attribute("address", address))
                    .andExpect(model().attribute("email", email))
                    .andExpect(model().attributeExists("emailError"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When form has been populated with data where the email is wrong, the user must be returned to the form with previously submitted data and a error message related to the email. Error: " + t.getMessage());
        }

        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(name, address, email));
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
                    .andExpect(model().attribute("name", name))
                    .andExpect(model().attribute("address", address))
                    .andExpect(model().attribute("email", email))
                    .andExpect(model().attributeExists("nameError"))
                    .andExpect(model().attributeExists("addressError"))
                    .andExpect(model().attributeExists("emailError"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When form has been populated with faulty data, the user must be returned to the form with previously submitted data and error messages related to all data entries. Error: " + t.getMessage());
        }

        assertFalse("The registration must not be added to the registrationService on failure.",
                registrationService.hasRegistration(name, address, email));
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
