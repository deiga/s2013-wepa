/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author timosand
 */
@Controller
public class HangmanController {
    
    @Autowired
    HangmanGameService gameService;
    
    @RequestMapping(value = "new", method = RequestMethod.GET)
    @ResponseBody
    public HangmanStatus createGame() {
        return gameService.initGame();
    }
    
    @RequestMapping(value = "{gameId}/status", method = RequestMethod.GET)
    @ResponseBody
    public HangmanStatus createGame(@PathVariable String gameId) {
        return gameService.getStatus(gameId);
    }
    
    @RequestMapping(value = "{gameId}/guess/{character}", method = RequestMethod.POST)
    @ResponseBody
    public HangmanStatus createGame(@PathVariable String gameId, @PathVariable String character) {
        return gameService.guess(gameId, character);
    }
    
}
