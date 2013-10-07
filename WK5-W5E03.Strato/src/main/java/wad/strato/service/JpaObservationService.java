/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.strato.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.strato.domain.Observation;
import wad.strato.repository.ObservationReposiory;

/**
 *
 * @author timosand
 */
@Service
public class JpaObservationService implements ObservationService {

    @Autowired
    private ObservationReposiory observationRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Observation> list(Integer page) {
        PageRequest request = new PageRequest(page-1, 5, Sort.Direction.DESC, "timestamp");
        return  observationRepository.findAll(request);
    }
}
