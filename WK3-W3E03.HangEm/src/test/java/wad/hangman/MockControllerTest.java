package wad.hangman;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
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
@Points("W3E03.1")
public class MockControllerTest {

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
    public void eachCreateReturnsNewGame() throws Exception {
        HangmanStatus game1 = createGame();
        HangmanStatus game2 = createGame();

        assertNotNull("When making a request to the address /new, a new game should be returned in JSON-format.", game1);
        assertNotNull("When making a request to the address /new, a new game should be returned in JSON-format.", game2);

        assertNotNull("The used characters should never be null.", game1.getUsed());
        
        assertFalse("Each new game should have a different game id.", game1.getId().equals(game2.getId()));
        assertTrue("Game state should be START at the beginning.", "START".equals(game1.getState()));
    }

    @Test
    public void statusReturnsGameState() throws Exception {
        HangmanStatus game = createGame();

        HangmanStatus status = getStatus(game.getId());
        assertNotNull("When making a request to the address /GAMEID/status, where GAMEID is the id of an existing game, the result should not be null.", status);
    }

    @Test
    public void guessingUpdatesGameStatus() throws Exception {
        HangmanStatus game = createGame();
        assertTrue("The used characters should be empty at the beginning.", game.getUsed().isEmpty());

        HangmanStatus status = guess(game.getId(), "a");
        assertNotNull("When making a guess to the address /GAMEID/guess/CHARACTER, where GAMEID is the id of an existing game, and CHARACTER is the guessed character, the result should not be null.", status);
        HangmanStatus postGuess = getStatus(game.getId());

        List<String> usedChars = postGuess.getUsed();

        assertTrue("When guessing a character, it should appear in the used characters.", usedChars.contains("a"));

        Integer wrong = postGuess.getWrong();
        String word = postGuess.getWord();

        assertTrue("After a character has been guessed, it should either increase the wrong count (if false guess), or be shown in the word (if correct guess).", word.contains("a") || wrong == 1);
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
