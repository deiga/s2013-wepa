package wad.moviedb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.moviedb.domain.Actor;
import wad.moviedb.domain.Movie;
import wad.moviedb.repository.ActorRepository;
import wad.moviedb.repository.MovieRepository;

@Service
public class RepositoryMovieService implements MovieService {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ActorRepository actorRepository;

    @Override
    public Iterable<Movie> list() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional
    public void add(String name, int lengthInMinutes) {
        Movie movie = new Movie();
        movie.setName(name);
        movie.setLengthInMinutes(lengthInMinutes);
        movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void remove(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        for (Actor actor : movie.getActors()) {
            actor.getMovies().remove(movie);
        }

        movieRepository.delete(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Movie> listMoviesWithout(Long actorId) {
        Actor actor = actorRepository.findOne(actorId);
        return movieRepository.findMoviesWithoutActor(actor);
    }
}
