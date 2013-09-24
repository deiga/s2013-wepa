package wad.flight.controller;

import org.springframework.ui.Model;

public interface AircraftControllerInterface {

    String add(String name);

    String assignAirport(Long aircraftId, Long airportId);

    String list(Model model);
}
