/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.rest.repository;

import org.springframework.data.repository.CrudRepository;
import wad.rest.domain.Sleep;

/**
 *
 * @author timosand
 */
public interface SleepRepository extends CrudRepository<Sleep, Long>{
    
}
