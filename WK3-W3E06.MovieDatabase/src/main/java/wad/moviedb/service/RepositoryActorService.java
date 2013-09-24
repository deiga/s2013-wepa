/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.moviedb.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.moviedb.domain.Actor;
import wad.moviedb.domain.Movie;
import wad.moviedb.repository.ActorRepository;

/**
 *
 * @author timosand
 */
@Service
public class RepositoryActorService implements ActorService {
    
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    MovieService movieService;

    @Override
    @Transactional(readOnly = false)
    public void delete(Long actorId) {
        Actor actor = actorRepository.findOne(actorId);
        List<Movie> movies = actor.getMovies();
        for (Movie m : movies) {
            m.getActors().remove(actor);
        }
        actorRepository.delete(actorId);
    }

    @Override
    @Transactional(readOnly = false)
    public void create(String name) {
        Actor newActor = new Actor();
        newActor.setName(name);
        actorRepository.save(newActor);
    }

    @Override
    @Transactional(readOnly = false)
    public void addMovies(Long actorId, Long movieId) {
        Actor addToMovie = actorRepository.findOne(actorId);
        Movie addToActor= movieService.get(movieId);
        addToMovie.getMovies().add(addToActor);
        addToActor.getActors().add(addToMovie);
    }

    @Override
    @Transactional(readOnly = true)
    public Actor get(Long actorId) {
        return actorRepository.findOne(actorId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Actor> list() {
        return actorRepository.findAll();
    }
    
}
