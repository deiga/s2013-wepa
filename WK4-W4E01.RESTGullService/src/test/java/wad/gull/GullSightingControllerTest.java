package wad.gull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import javax.annotation.Resource;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;
import wad.gull.domain.GullSighting;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@WebAppConfiguration
@Points("W4E01")
public class GullSightingControllerTest {

    @Resource
    private WebApplicationContext waco;
    private MockMvc controllerMock;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        controllerMock = webAppContextSetup(waco).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void singlePost() throws Exception {
        GullSighting sighting = new GullSighting();
        sighting.setLocation("kumpula,exactum,kahvihuone");
        
        GullSighting result = postSighting(sighting);

        if (result.getId() != null) {
            attemptDelete(result.getId());
        } else {
            fail("Each GullSighting should have an assigned id after it has been posted.");
        }
    }

    @Test
    public void postAndGet() throws Exception {
        GullSighting sighting = new GullSighting();
        sighting.setLocation("Kahvihuone");

        GullSighting result = postSighting(sighting);

        List<GullSighting> sightings = getGulls();
        
        boolean found = false;
        for (GullSighting gullSighting : sightings) {
            if("Kahvihuone".equals(gullSighting.getLocation())) {
                found = true;
                break;
            }
        }

        assertTrue("After Lokki has been spotted in Kahvihuone, the sightings should include Kahvihuone. Now the sightings were:\n" + sightings.toString(), found);

        if (result.getId() != null) {
            attemptDelete(result.getId());
        } else {
            fail("Each GullSighting should have an assigned id after it has been posted.");
        }
    }

    @Test
    public void postGetDeleteGet() throws Exception {
        postAndGet();

        List<GullSighting> sightings = getGulls();
        boolean found = false;
        for (GullSighting gullSighting : sightings) {
            if ("Kahvihuone".equals(gullSighting.getLocation())) {
                found = true;
                break;
            }
        }

        assertFalse("After a single sighting has been removed, it should not be available anymore. Now the sightings were:\n" + sightings.toString(), found);
    }

    @Test
    public void doublePostWithGetWorks() throws Exception {

        GullSighting sighting = new GullSighting();
        sighting.setLocation("kumpula,exactum,kahvihuone");
        
        postSighting(sighting);
        
        
        List<GullSighting> sightings = getGulls();
        boolean found = false;
        for (GullSighting gullSighting : sightings) {
            if ("exactum,b227".equals(gullSighting.getLocation())) {
                fail("Lokki has not been sighted at b227 yet.");
            }
            if("kumpula,exactum,kahvihuone".equals(gullSighting.getLocation())) {
                found = true;
            }
        }

        assertTrue("After Lokki has been spotted in Kahvihuone, the sightings should include Kahvihuone. Now the sightings were:\n" + sightings.toString(), found);

        sighting = new GullSighting();
        sighting.setLocation("exactum,b227");
        
        postSighting(sighting);
        
        sightings = getGulls();
        boolean foundKahvihuone = false;
        boolean foundB227 = false;
        for (GullSighting gullSighting : sightings) {
            if ("exactum,b227".equals(gullSighting.getLocation())) {
                foundB227 = true;
            }
            if("kumpula,exactum,kahvihuone".equals(gullSighting.getLocation())) {
                foundKahvihuone = true;
            }
        }

        
        assertTrue("After Lokki has been spotted in Kahvihuone, the sightings should include Kahvihuone. Now the sightings were:\n" + sightings.toString(), foundKahvihuone);

        assertTrue("After Lokki has been spotted in B227, the sightings should include B227. Now the sightings were:\n" + sightings.toString(), foundB227);

        
        for (GullSighting gullSighting : sightings) {
            attemptDelete(gullSighting.getId());
        }
    }


    private void deleteGull(Long itemId) throws Exception {
        controllerMock.perform(delete("/gulls/{id}", itemId)).andExpect(status().isOk());
    }
    
    private void attemptDelete(Long id) throws Exception {
        deleteGull(id);
    }

    private GullSighting postSighting(GullSighting sighting) throws Exception {
        MvcResult result = controllerMock
                .perform(post("/gulls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(sighting)))
                .andReturn();
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<GullSighting>() {
        });
    }
    
    
    private List<GullSighting> getGulls() throws Exception {
        MvcResult result = controllerMock.perform(get("/gulls"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<List<GullSighting>>() {
        });
    }
}