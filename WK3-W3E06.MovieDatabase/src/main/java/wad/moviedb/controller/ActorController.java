/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.moviedb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.moviedb.service.ActorService;
import wad.moviedb.service.MovieService;

/**
 *
 * @author timosand
 */
@Controller
public class ActorController {
    @Autowired
    private ActorService actorService;
    @Autowired
    private MovieService movieService;
    
    @RequestMapping(value = "actors", method = RequestMethod.GET)
    public String listActors(Model model) {
        model.addAttribute("actors", actorService.list());
        return "actors";
    }
    
    @RequestMapping(value = "actors/{actorId}", method = RequestMethod.GET)
    public String showActor(@PathVariable Long actorId, Model model) {
        model.addAttribute("actor", actorService.get(actorId));
        model.addAttribute("movies", movieService.list());
        return "actor";
    }
    
    @RequestMapping(value = "actors/{actorId}/movies", method = RequestMethod.POST)
    public String addMovieToActor(@PathVariable Long actorId, @RequestParam Long movieId) {
        actorService.addMovies(actorId, movieId);
        return "redirect:/app/actors";
    }
    
    @RequestMapping(value = "actors", method = RequestMethod.POST, params = "name")
    public String addActor(@RequestParam String name) {
        actorService.create(name);
        return "redirect:/app/actors";
    }
    
    @RequestMapping(value = "actors/{actorId}/delete", method = RequestMethod.POST)
    public String deleteActor(@PathVariable Long actorId) {
        actorService.delete(actorId);
        return "redirect:/app/actors";
    }
    
}
