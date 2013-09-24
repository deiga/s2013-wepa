package wad.flight.controller;

import org.springframework.ui.Model;

public interface AirportControllerInterface {

    String add(String identifier, String name);

    String list(Model model);
}
