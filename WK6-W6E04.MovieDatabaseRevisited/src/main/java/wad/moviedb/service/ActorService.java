package wad.moviedb.service;

import wad.moviedb.domain.Actor;

public interface ActorService {
    Iterable<Actor> list();
    void add(String name);    
    void remove(Long actorId);
    Actor findById(Long actorId);
    void addActorToMovie(Long actorId, Long movieId);
}
