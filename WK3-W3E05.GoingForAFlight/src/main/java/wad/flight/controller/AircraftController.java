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

    @RequestMapping(value = "aircrafts/{aircraftId}/airports", method = RequestMethod.POST, params = "airportId")
    @Override
    public String assignAirport(@PathVariable Long aircraftId, @RequestParam Long airportId) {
        Aircraft update = aircraftRepository.findOne(aircraftId);
        Airport foo = airportRepository.findOne(airportId);
        update.setAirport(foo);
        foo.getAircrafts().add(update);
        aircraftRepository.save(update);
        airportRepository.save(foo);
        
        return "redirect:/app/aircrafts/";
    }
    
}
