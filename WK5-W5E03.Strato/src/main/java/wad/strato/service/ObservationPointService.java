package wad.strato.service;

import java.util.List;
import wad.strato.domain.Observation;
import wad.strato.domain.ObservationPoint;

public interface ObservationPointService {

    List<ObservationPoint> list();

    void create(ObservationPoint point);

    ObservationPoint read(Long observationPointId);

    void addObservation(Observation obs, Long observationPointId);
}
