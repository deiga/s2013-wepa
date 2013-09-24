/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.highfive.controller;

import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.highfive.data.Score;
import wad.highfive.service.GameService;
import wad.highfive.service.ScoreService;

/**
 *
 * @author timosand
 */
@Controller
public class ScoreController {
    
    @Autowired
    ScoreService scoreService;
    @Autowired
    private GameService gameService;
    
    @RequestMapping(value = "games/{name}/scores", method = RequestMethod.POST)
    @ResponseBody
    public Score createScore(@PathVariable String name, @RequestBody Score score) {
        score.setGame(gameService.findByName(name));
        score.setTimestamp(new Date());
        return scoreService.create(score);
    }
    
    @RequestMapping(value = "games/{name}/scores", method = RequestMethod.GET)
    @ResponseBody
    public Collection<Score> listScores(@PathVariable String name) {
        return scoreService.findByGame(gameService.findByName(name));
    }
    
    @RequestMapping(value = "games/{name}/scores/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Score getScore(@PathVariable String name, @PathVariable Long id) {
        return scoreService.findByGameAndId(gameService.findByName(name), id);
    }
    
    @RequestMapping(value = "games/{name}/scores/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Score deleteScore(@PathVariable String name, @PathVariable Long id) {
        Score toDelete = scoreService.findByGameAndId(gameService.findByName(name), id);
        scoreService.delete(toDelete);
        return toDelete;
    }
    
}
