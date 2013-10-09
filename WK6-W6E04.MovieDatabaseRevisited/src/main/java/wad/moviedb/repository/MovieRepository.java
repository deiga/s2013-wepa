package wad.moviedb.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import wad.moviedb.domain.Actor;
import wad.moviedb.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT movie FROM MOVIE movie WHERE :actor NOT MEMBER OF movie.actors")
    List<Movie> findMoviesWithoutActor(@Param("actor") Actor actor);
}
