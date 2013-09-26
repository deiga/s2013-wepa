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

        boolean error = false;
        if (name.length() < 4 || name.length() > 30) {
            error = true;
            model.addAttribute("nameError", "Length of name should be between 4 and 30.");
        }

        if (address.length() < 4 || address.length() > 50) {
            error = true;
            model.addAttribute("addressError", "Length of address should be between 4 and 50.");
        }

        if (!email.contains("@")) {
            error = true;
            model.addAttribute("emailError", "Email should contain a @-character.");
        }
        if (error) {
            model.addAttribute("name", name);
            model.addAttribute("address", address);
            model.addAttribute("email", email);

            return "form";
        }

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
