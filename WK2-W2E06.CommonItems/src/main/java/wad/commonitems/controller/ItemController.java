package wad.commonitems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.commonitems.domain.Item;
import wad.commonitems.service.ItemService;

@Controller
public class ItemController {
    
    @Qualifier(value = "restItemService")
    @Autowired
    ItemService itemService;

   
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addItem(@RequestParam String name, @RequestParam String description) {
        Item toAdd = new Item();
        toAdd.setName(name);
        toAdd.setDescription(description);
        itemService.add(toAdd);
        return "redirect:list";
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteItem(@RequestParam Integer id) {
        itemService.delete(id);
        return "redirect:list";
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String listItems(Model model) {
        model.addAttribute("items", itemService.list());
        return "list";
    }
}
