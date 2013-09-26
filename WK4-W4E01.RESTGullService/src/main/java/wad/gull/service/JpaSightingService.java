package wad.gull.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.gull.domain.GullSighting;
import wad.gull.repository.SightingRepository;

@Service
public class JpaSightingService implements GullSightingService {

    @Autowired
    private SightingRepository sightingRepository;

    @Override
    @Transactional(readOnly = false)
    public GullSighting create(GullSighting gull) {
        return sightingRepository.save(gull);
    }

    @Override
    @Transactional(readOnly = true)
    public GullSighting read(Long identifier) {
        return sightingRepository.findOne(identifier);
    }

    @Override
    @Transactional(readOnly = false)
    public GullSighting update(Long identifier, GullSighting gull) {
        delete(identifier);
        gull.setId(identifier);
        return create(gull);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Long id) {
        sightingRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GullSighting> list() {
        return (List<GullSighting>) sightingRepository.findAll();
    }
}