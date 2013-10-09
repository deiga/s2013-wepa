package wad.moviedb.repository;

import org.springframework.data.repository.CrudRepository;
import wad.moviedb.domain.Actor;

public interface ActorRepository extends CrudRepository<Actor, Long> {
}
