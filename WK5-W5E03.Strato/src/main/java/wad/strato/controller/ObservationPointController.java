package wad.strato.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.strato.domain.ObservationPoint;
import wad.strato.service.ObservationPointService;

@Controller
@RequestMapping("observationpoint")
public class ObservationPointController {

    @Autowired
    private ObservationPointService observationPointService;

    @RequestMapping(method = RequestMethod.GET)
    public String getPoints(Model model, @ModelAttribute("observationPoint") ObservationPoint point) {
        model.addAttribute("points", observationPointService.list());
        return "points";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPoint(@Validated @ModelAttribute("observationPoint") ObservationPoint point, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "points";
        }
        observationPointService.create(point);
        return "redirect:observationpoint";
    }
}
