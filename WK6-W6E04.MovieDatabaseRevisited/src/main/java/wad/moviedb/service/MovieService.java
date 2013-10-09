package wad.moviedb.service;

import wad.moviedb.domain.Movie;

public interface MovieService {
    Iterable<Movie> list();
    void add(String name, int lengthInMinutes);
    void remove(Long movieId);
    Iterable<Movie> listMoviesWithout(Long actorId);
}
