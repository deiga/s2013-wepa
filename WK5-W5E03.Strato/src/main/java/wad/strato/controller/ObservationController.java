package wad.strato.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.strato.controller.form.ObservationFormObject;
import wad.strato.domain.Observation;
import wad.strato.service.ObservationPointService;
import wad.strato.service.ObservationService;

@Controller
@RequestMapping("observation")
public class ObservationController {

    @Autowired
    private ObservationPointService observationPointService;
    @Autowired
    private ObservationService observationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getObservations(Model model, @ModelAttribute("formObject") ObservationFormObject form, @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNumber) {
        model.addAttribute("observationpoints", observationPointService.list());
        Page<Observation> page = observationService.list(pageNumber);
        model.addAttribute("observations", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages() + 1);
        model.addAttribute("pageNumber", pageNumber);
        return "observations";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addObservation(Model model, @Validated @ModelAttribute("formObject") ObservationFormObject form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("observationpoints", observationPointService.list());
            return "observations";
        }
        Observation obs = new Observation();
        obs.setCelsius(form.getCelsius());
        obs.setTimestamp(new Date());

        observationPointService.addObservation(obs, form.getObservationPointId());
        return "redirect:observation";
    }
}
