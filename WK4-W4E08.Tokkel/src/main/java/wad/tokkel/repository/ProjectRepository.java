/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.tokkel.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wad.tokkel.models.Project;

/**
 *
 * @author timosand
 */
@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer>{
    
}
