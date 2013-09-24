package wad.flight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.flight.domain.Airport;
import wad.flight.repository.AirportRepository;

@Controller
public class AirportController implements AirportControllerInterface {

    @Autowired
    private AirportRepository airportRepository;

    @RequestMapping(value = "airports", method = RequestMethod.GET)
    @Override
    public String list(Model model) {
        model.addAttribute("airports", airportRepository.findAll());
        return "airports";
    }

    @RequestMapping(value = "airports", method = RequestMethod.POST)
    @Override
    public String add(@RequestParam String identifier, @RequestParam String name) {
        Airport airport = new Airport();
        airport.setIdentifier(identifier);
        airport.setName(name);

        airportRepository.save(airport);
        return "redirect:/app/airports/";
    }
}
