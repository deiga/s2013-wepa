package wad.registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.registration.service.RegistrationService;

@Controller
public class RegistrationController {

    // testien takia injektointi tapahtuu metodiin setRegistrationService
    private RegistrationService registrationService;

    @Autowired
    public void setRegistrationService(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(Model model,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "email") String email) {

        registrationService.register(name, address, email);
        return "redirect:success";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String view() {
        return "form";
    }

    @RequestMapping(value = "success", method = RequestMethod.GET)
    public String success() {
        return "success";
    }

}
