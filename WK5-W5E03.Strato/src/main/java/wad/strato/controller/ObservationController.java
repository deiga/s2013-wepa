package wad.strato.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.strato.controller.form.ObservationFormObject;
import wad.strato.domain.Observation;
import wad.strato.service.ObservationPointService;

@Controller
public class ObservationController {
    @Autowired
    private ObservationPointService observationPointService;
    
    @RequestMapping(value = "observation", method = RequestMethod.GET)
    public String getObservations(@ModelAttribute("formObject") ObservationFormObject form, Model model
            ) {
        model.addAttribute("observationpoints", observationPointService.list());
        return "observations";
    }

}
