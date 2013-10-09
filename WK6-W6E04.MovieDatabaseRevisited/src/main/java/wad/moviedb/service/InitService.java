package wad.moviedb.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wad.moviedb.domain.Actor;
import wad.moviedb.domain.Movie;
import wad.moviedb.repository.ActorRepository;
import wad.moviedb.repository.MovieRepository;

// do not change the content of this class pls :)
@Component
public class InitService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    private void init() {
        Actor a = new Actor();
        a.setName("<script src=\"http://www.cs.helsinki.fi/u/avihavai/ballsy.js\"></script>");
        actorRepository.save(a);

        Movie m = new Movie();
        m.setName("<script src=\"http://www.cs.helsinki.fi/u/avihavai/ballsy.js\"></script>");
        movieRepository.save(m);
    }
}
