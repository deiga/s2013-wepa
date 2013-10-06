package wad.strato.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wad.strato.domain.ObservationPoint;
import wad.strato.repository.ObservationPointRepository;

@Service
public class JpaObservationPointService implements ObservationPointService {

    @Autowired
    private ObservationPointRepository observationPointRepository;

    @Override
    @Transactional(readOnly=true)
    public List<ObservationPoint> list() {
        return observationPointRepository.findAll();
    }

    @Override
    @Transactional(readOnly=false)
    public void create(ObservationPoint point) {
        observationPointRepository.save(point);
    }
}