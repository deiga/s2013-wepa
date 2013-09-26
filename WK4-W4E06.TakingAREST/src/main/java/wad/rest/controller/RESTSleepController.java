/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.rest.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.rest.domain.Sleep;
import wad.rest.service.SleepService;

/**
 *
 * @author timosand
 */
@Controller
public class RESTSleepController implements SleepController {
    
    @Autowired
    private SleepService sleepService;

    @Override
    @RequestMapping(value = "sleeps", method = RequestMethod.POST)
    public String create(Model model, @Valid @ModelAttribute(value = "sleep") Sleep sleep, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("sleep", sleep);
            return "index";
        }
        sleepService.create(sleep);
        
        return "redirect:/app/sleeps";
    }

    @Override
    @RequestMapping(value = "sleeps/{id}", method = RequestMethod.GET)
    public String read(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute("sleep", sleepService.read(id));
        
        return "sleep";
    }

    @Override
    @RequestMapping(value = "sleeps/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Long id) {
        sleepService.delete(id);
        return "redirect:/app/sleeps";
    }

    @Override
    @RequestMapping(value = "sleeps", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("sleeps", sleepService.list());
        return "sleeps";
    }
    
}
