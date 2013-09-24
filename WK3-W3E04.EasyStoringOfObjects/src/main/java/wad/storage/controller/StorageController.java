/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.storage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.storage.domain.Item;
import wad.storage.repository.ItemRepository;

/**
 *
 * @author timosand
 */
@Controller
public class StorageController implements StorageControllerInterface{
    
    @Autowired
    ItemRepository itemRepository;

    @Override
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public String view(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "view";
    }

    @Override
    @RequestMapping(value = "add", method = RequestMethod.POST, params = "name")
    public String add(@RequestParam String name) {
        Item created = new Item();
        created.setName(name);
        created.setCount(1);
        itemRepository.save(created);
        
        return "redirect:view";
    }

    @Override
    @RequestMapping(value = "increaseCount", method = RequestMethod.POST, params = "itemId")
    public String increaseCount(@RequestParam Long itemId) {
        Item update = itemRepository.findOne(itemId);
        update.setCount(update.getCount()+1);
        itemRepository.save(update);
        
        return "redirect:view";
    }
    
}
