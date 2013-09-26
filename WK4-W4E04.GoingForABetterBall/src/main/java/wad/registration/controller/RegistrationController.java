package wad.registration.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.registration.domain.Registration;
import wad.registration.service.RegistrationService;

@Controller
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute("registration") Registration registration,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }

        registrationService.register(registration);
        return "redirect:success";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String view(@ModelAttribute("registration") Registration registration) {
        return "form";
    }

    @RequestMapping(value = "success", method = RequestMethod.GET)
    public String success() {
        return "success";
    }
}