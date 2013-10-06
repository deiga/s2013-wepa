package wad.strato.controller;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.strato.domain.ObservationPoint;
import wad.strato.service.ObservationPointService;

@Controller
public class ObservationPointController {

    @Autowired
    private ObservationPointService observationPointService;
    

    @RequestMapping(value = "observationpoint", method = RequestMethod.GET)
    public String getPoints(Model model, @ModelAttribute("observationPoint") ObservationPoint point) {
        model.addAttribute("points", observationPointService.list());
        return "points";
    }

}
