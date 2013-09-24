/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.highfive.controller;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.highfive.data.Game;
import wad.highfive.service.GameService;

/**
 *
 * @author timosand
 */
@Controller
public class GameController {
    @Autowired
    private GameService gameService;
    
    @RequestMapping(value = "games", method = RequestMethod.POST)
    @ResponseBody
    public Game createGame(@RequestBody Game game) {
        return gameService.create(game);
    }
    
    @RequestMapping(value = "games", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Game> listGames() {
        return gameService.findAll();
    }
    
    @RequestMapping(value = "games/{name}", method = RequestMethod.GET)
    @ResponseBody
    public Game getGame(@PathVariable String name) {
        return gameService.findByName(name);
    }
    
    @RequestMapping(value = "games/{name}", method = RequestMethod.DELETE)
    @ResponseBody
    public Game deleteGame(@PathVariable String name) {
        Game toDelete = gameService.findByName(name);
        gameService.delete(toDelete);
        return toDelete;
    }
    
}
