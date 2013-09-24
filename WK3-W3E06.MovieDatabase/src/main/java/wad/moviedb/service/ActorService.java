/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.moviedb.service;

import wad.moviedb.domain.Actor;

/**
 *
 * @author timosand
 */
public interface ActorService {

    public void delete(Long actorId);

    public void create(String name);

    public void addMovies(Long actorId, Long movieId);

    public Actor get(Long actorId);

    public Iterable<Actor> list();
    
}
