package wad.gull.service;

import java.util.List;
import wad.gull.domain.GullSighting;

public interface GullSightingService {
    GullSighting create(GullSighting sighting);
    GullSighting read(Long identifier);
    GullSighting update(Long identifier, GullSighting sighting);
    void delete(Long identifier);

    List<GullSighting> list();
}