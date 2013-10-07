/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.strato.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.strato.domain.Observation;

/**
 *
 * @author timosand
 */
public interface ObservationReposiory extends JpaRepository<Observation, Long> {
    
    Page<Observation> findAll(Pageable pageable);
}
