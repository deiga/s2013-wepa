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

@Controller
public class ActorController {

    @Autowired
    private ActorService actorService;
    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "actors", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("actors", actorService.list());
        return "actors";
    }

    @RequestMapping(value = "actors", method = RequestMethod.POST)
    public String add(@RequestParam String name) {
        actorService.add(name);
        return "redirect:/app/actors";
    }

    @RequestMapping(value = "actors/{actorId}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable(value = "actorId") Long actorId) {
        model.addAttribute("actor", actorService.findById(actorId));
        model.addAttribute("movies", movieService.listMoviesWithout(actorId));
        return "actor";
    }

    @RequestMapping(value = "actors/{actorId}/delete", method = RequestMethod.POST)
    public String add(@PathVariable(value = "actorId") Long actorId) {
        actorService.remove(actorId);
        return "redirect:/app/actors";
    }

    @RequestMapping(value = "actors/{actorId}/movies", method = RequestMethod.POST)
    public String addActorToMovie(@PathVariable(value = "actorId") Long actorId,
            @RequestParam(value = "movieId") Long movieId) {
        actorService.addActorToMovie(actorId, movieId);
        return "redirect:/app/actors";
    }
}
