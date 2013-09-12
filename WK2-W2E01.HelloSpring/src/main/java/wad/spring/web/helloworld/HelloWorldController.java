/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.spring.web.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author timosand
 */
@Controller
public class HelloWorldController {
    
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String sayHello(Model model) {
        model.addAttribute("message", "Great Scott!");
        return "hello";
    }
    
    
}