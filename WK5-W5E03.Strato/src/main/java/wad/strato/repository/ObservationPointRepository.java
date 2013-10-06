package wad.strato.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.strato.domain.ObservationPoint;

public interface ObservationPointRepository extends JpaRepository<ObservationPoint, Long> {
}