package wad.parlezvousfrancais.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.parlezvousfrancais.service.CounterService;

@Controller
public class ParlezVousFrancaisController {

    @Autowired
    private CounterService counterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String ask(Model model) {

        model.addAttribute("counter", counterService.value());

        return "question";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String answer(@RequestParam String answer) {

        counterService.increment();
        
        if (answer.equals("yes")) {
            return "yes";
        }

        return "no";
    }
}
