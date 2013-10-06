package wad.heavycalc;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.UUID;
import javax.annotation.Resource;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

@Points("W5E05")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/spring-base.xml"})
@WebAppConfiguration
public class HeavyCalculationsTest {

    @Resource
    private WebApplicationContext waco;
    private MockMvc controllerMock;

    @Before
    public void setup() {
        controllerMock = webAppContextSetup(waco).build();
    }

    @Test
    public void formReturnedOnGetToOrder() throws Exception {
        controllerMock
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
    }

    @Test
    public void hasFlashAttributeMessageAndDoesLongProcessingWhenNotConfigured() throws Exception {
        
        long start = System.nanoTime();
        String data = UUID.randomUUID().toString().substring(0, 6);

        MvcResult result = controllerMock
                .perform(post("/")
                .param("data", data))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", " sent for processing!"))
                .andReturn();
        
        assertTrue("The user should be redirected to another page after a successful POST.", result.getModelAndView().getViewName().startsWith("redirect:"));
        
        try {
            hasFlashAttributeMessageAndDoesLongProcessingWhenConfigured();
            return;
        } catch (Throwable t) {
        }
        
        
        long end = System.nanoTime();
        long eightSecondsInNanoSeconds = 8000000000L;
        assertTrue("Do not remove the Thread-sleep calls from the InMemoryDataProcessor.", (end-start > eightSecondsInNanoSeconds));
    }
    
    
    @Test
    public void hasFlashAttributeMessageAndDoesLongProcessingWhenConfigured() throws Exception {

        long start = System.nanoTime();
        String data = UUID.randomUUID().toString().substring(0, 6);

    
        MvcResult result = controllerMock
                .perform(post("/")
                .param("data", data))
                .andExpect(flash().attributeCount(1))
                .andExpect(flash().attribute("message", " sent for processing!"))
                .andReturn();

        assertTrue("The user should be redirected to another page after a successful POST.", result.getModelAndView().getViewName().startsWith("redirect:"));
        
        long end = System.nanoTime();
        long twoSecondsInNanoSeconds = 2000000000L;
        assertTrue("You must configure the DataProcessor so that it does work asynchronously.", (end-start < twoSecondsInNanoSeconds));
    }
}
