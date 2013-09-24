package wad.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.flight.domain.Aircraft;
import wad.flight.domain.Airport;
import wad.flight.repository.AircraftRepository;
import wad.flight.repository.AirportRepository;

@Controller
public class AircraftController implements AircraftControllerInterface {

    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(value = "aircrafts", method = RequestMethod.GET)
    @Override
    public String list(Model model) {
        model.addAttribute("aircrafts", aircraftRepository.findAll());
        model.addAttribute("airports", airportRepository.findAll());

        return "aircrafts";
    }

    @RequestMapping(value = "aircrafts", method = RequestMethod.POST)
    @Override
    public String add(@RequestParam String name) {
        Aircraft aircraft = new Aircraft();
        aircraft.setName(name);
        aircraftRepository.save(aircraft);

        return "redirect:/app/aircrafts/";
    }

    public String assignAirport(Long aircraftId, Long airportId) {
    return null; // got fired before I got to finish this :(
    }
    
}
