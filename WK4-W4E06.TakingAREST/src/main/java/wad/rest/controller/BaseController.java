package wad.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.rest.domain.Sleep;

@Controller
public class BaseController {
    
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String getIndex(@ModelAttribute Sleep sleep) {
        
        return "index";
    }
}