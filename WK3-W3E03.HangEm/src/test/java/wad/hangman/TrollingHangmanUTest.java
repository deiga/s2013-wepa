package wad.hangman;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.cs.tmc.edutestutils.Points;
import javax.annotation.Resource;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring-base.xml")
@WebAppConfiguration
@Points("W3E03.2")
public class TrollingHangmanUTest {

    @Resource
    private WebApplicationContext waco;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(waco).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void ableToWinGameDueToBadTrollingStrategy() throws Exception {
        HangmanStatus game = createGame();
        guess(game.getId(), "a");
        guess(game.getId(), "e");
        guess(game.getId(), "i");
        guess(game.getId(), "u");

        HangmanStatus status = getStatus(game.getId());
        assertTrue("With the new strategy, after guessing a e i and u, I should have 4 wrong.", status.getWrong() == 4);

        guess(game.getId(), "t");
        guess(game.getId(), "o");
        guess(game.getId(), "m");
        guess(game.getId(), "r");
        guess(game.getId(), "w");


        status = getStatus(game.getId());
        assertTrue("With the trolling strategy and current wordset, after guessing a e i and u, and guessing tomorrow, I should win the game.", status.getState().equals("WIN"));
    }

    private HangmanStatus createGame() throws Exception {
        MvcResult result = mockMvc.perform(get("/new"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<HangmanStatus>() {
        });
    }

    private HangmanStatus getStatus(String id) throws Exception {
        MvcResult result = mockMvc.perform(get("/{id}/status", id))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        return mapper.readValue(content, new TypeReference<HangmanStatus>() {
        });
    }

    private HangmanStatus guess(String id, String character) throws Exception {
        MvcResult result = mockMvc.perform(post("/{id}/guess/{character}", id, character))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        return mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<HangmanStatus>() {
        });
    }
}
