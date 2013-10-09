package wad.moviedb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.moviedb.service.MovieService;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "movies", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("movies", movieService.list());
        return "movies";
    }

    @RequestMapping(value = "movies", method = RequestMethod.POST)
    public String add(@RequestParam String name, @RequestParam Integer lengthInMinutes) {
        movieService.add(name, lengthInMinutes);
        return "redirect:/app/movies";
    }

    @RequestMapping(value = "movies/{movieId}/delete", method = RequestMethod.POST)
    public String add(@PathVariable(value = "movieId") Long movieId) {
        movieService.remove(movieId);
        return "redirect:/app/movies";
    }
}
