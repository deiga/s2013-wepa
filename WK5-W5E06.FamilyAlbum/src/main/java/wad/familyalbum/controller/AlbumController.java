/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.familyalbum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.familyalbum.service.ImageService;

/**
 *
 * @author timosand
 */
@Controller
public class AlbumController {
    
    @Autowired
    ImageService imageService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("images", imageService.list());
        return "album";
    }
    
}
