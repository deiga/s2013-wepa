/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.rest.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.rest.domain.Sleep;
import wad.rest.repository.SleepRepository;

/**
 *
 * @author timosand
 */
@Service
public class JpaSleepService implements SleepService {
    
    @Autowired
    private SleepRepository sleepRepository;

    @Override
    @Transactional
    public Sleep create(Sleep sleep) {
        return sleepRepository.save(sleep);
    }

    @Override
    @Transactional(readOnly = true)
    public Sleep read(Long id) {
        return sleepRepository.findOne(id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        sleepRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sleep> list() {
        return (List<Sleep>) sleepRepository.findAll();
    }
    
}
