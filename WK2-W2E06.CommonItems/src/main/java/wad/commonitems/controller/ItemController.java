package wad.commonitems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addItem(@RequestParam String name, @RequestParam String description) {
        return null;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteItem(@RequestParam Integer id) {
        return null;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listItems(Model model) {
        return null;
    }
}
