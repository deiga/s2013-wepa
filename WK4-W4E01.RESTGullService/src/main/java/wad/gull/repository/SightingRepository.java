package wad.gull.repository;

import org.springframework.data.repository.CrudRepository;
import wad.gull.domain.GullSighting;

public interface SightingRepository extends CrudRepository<GullSighting, Long> {

}