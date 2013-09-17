/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.hangman.game.Game;
import wad.hangman.service.HangmanService;

/**
 *
 * @author timosand
 */
@Controller
public class HangmanController {
    
    @Qualifier(value = "backendHangmanService")
    @Autowired
    HangmanService hangman;
    
    Game game;
    
    @RequestMapping(value = "start", method = RequestMethod.GET)
    public String start(Model model) {
        game = hangman.startGame();
        model.addAttribute("word", game.getWord());
        model.addAttribute("state", game.getState());
        model.addAttribute("used", game.getUsed());
        model.addAttribute("gameid", game.getId());
        model.addAttribute("error", game.getWrong());
        
        return "hangman";
    }
    
    @RequestMapping(value = "guess", method = RequestMethod.POST)
    public String guess(@RequestParam(value = "guess", required = true) Character guess, @RequestParam(value = "gameid") String gameId, Model model) {
        game = hangman.guess(gameId, guess);
        model.addAttribute("word", game.getWord());
        model.addAttribute("state", game.getState());
        model.addAttribute("used", game.getUsed());
        model.addAttribute("gameid", game.getId());
        model.addAttribute("error", game.getWrong());
        
        return "hangman";
    }
    
}
