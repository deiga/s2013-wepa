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
import wad.moviedb.service.MovieService;

/**
 *
 * @author timosand
 */
@Controller
public class MovieController {
    
    @Autowired
    MovieService movieService;
    
    @RequestMapping(value = "movies", method = RequestMethod.GET)
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.list());
        return "movies";
    }
    
    @RequestMapping(value = "movies", method = RequestMethod.POST, params = {"name", "lengthInMinutes"})
    public String addMovie(@RequestParam String name, @RequestParam Integer lengthInMinutes) {
        movieService.create(name, lengthInMinutes);
        return "redirect:/app/movies";
    }
    
    @RequestMapping(value = "movies/{movieId}/delete", method = RequestMethod.POST)
    public String deleteMovie(@PathVariable Long movieId) {
        movieService.delete(movieId);
        return "redirect:/app/movies";
    }
    
}
