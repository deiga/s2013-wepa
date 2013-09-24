/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.moviedb.service;

import wad.moviedb.domain.Movie;

/**
 *
 * @author timosand
 */
public interface MovieService {

    public void delete(Long movieId);

    public void create(String name, Integer length);

    public Iterable<Movie> list();

    public Movie get(Long movieId);
    
}
