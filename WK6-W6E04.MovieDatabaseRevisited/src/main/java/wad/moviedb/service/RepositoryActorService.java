package wad.moviedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.moviedb.domain.Actor;
import wad.moviedb.domain.Movie;
import wad.moviedb.repository.ActorRepository;
import wad.moviedb.repository.MovieRepository;

@Service
public class RepositoryActorService implements ActorService {

    @Autowired
    private ActorRepository actorRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Iterable<Actor> list() {
        return actorRepository.findAll();
    }

    @Override
    @Transactional
    public void add(String name) {
        Actor actor = new Actor();
        actor.setName(name);

        actorRepository.save(actor);
    }

    @Override
    @Transactional
    public void remove(Long actorId) {
        Actor actor = actorRepository.findOne(actorId);
        for (Movie movie : actor.getMovies()) {
            movie.getActors().remove(actor);
        }

        actorRepository.delete(actorId);
    }

    @Override
    @Transactional
    public void addActorToMovie(Long actorId, Long movieId) {
        Actor actor = actorRepository.findOne(actorId);
        Movie movie = movieRepository.findOne(movieId);

        actor.getMovies().add(movie);
        movie.getActors().add(actor);
    }

    @Override
    public Actor findById(Long actorId) {
        return actorRepository.findOne(actorId);
    }
}
