package wad.flight.repository;

import org.springframework.data.repository.CrudRepository;
import wad.flight.domain.Airport;

public interface AirportRepository extends CrudRepository<Airport, Long> {
}
