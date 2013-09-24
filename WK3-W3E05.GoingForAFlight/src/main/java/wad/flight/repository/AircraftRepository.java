package wad.flight.repository;

import org.springframework.data.repository.CrudRepository;
import wad.flight.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
