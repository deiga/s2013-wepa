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
import wad.moviedb.repository.MovieRepository;

/**
 *
 * @author timosand
 */
@Service
public class RepositoryMovieService implements MovieService {
    
    @Autowired
    MovieRepository movieRepository;
    
    @Transactional(readOnly = false)
    @Override
    public void create(String movieName, Integer timeInMinutes) {
        Movie newMovie = new Movie();
        newMovie.setName(movieName);
        newMovie.setLengthInMinutes(timeInMinutes);
        movieRepository.save(newMovie);
    }
    
    @Transactional(readOnly = true)
    @Override
    public Iterable<Movie> list() {
        return movieRepository.findAll();
    }

    @Override
    @Transactional(readOnly =  false)
    public void delete(Long movieId) {
        Movie movie = movieRepository.findOne(movieId);
        List<Actor> actors = movie.getActors();
        for (Actor a : actors) {
            a.getMovies().remove(movie);
        }
        movieRepository.delete(movieId);
        
    }

    @Override
    @Transactional(readOnly = true)
    public Movie get(Long movieId) {
        return movieRepository.findOne(movieId);
    }
    
}
