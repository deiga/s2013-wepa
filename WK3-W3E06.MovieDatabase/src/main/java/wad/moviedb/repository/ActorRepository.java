/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.moviedb.repository;

import org.springframework.data.repository.CrudRepository;
import wad.moviedb.domain.Actor;

/**
 *
 * @author timosand
 */
public interface ActorRepository extends CrudRepository<Actor, Long> {
    
}
