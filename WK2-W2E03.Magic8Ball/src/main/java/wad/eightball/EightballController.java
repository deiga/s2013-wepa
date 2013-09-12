/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.eightball;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author timosand
 */
@Controller
public class EightballController {
    
    @Autowired
    EightballService eightball;
    
    @RequestMapping(value = "ask", method = RequestMethod.POST)
    public String ask8Ball(@RequestParam(value = "question") String question, Model model) {
        model.addAttribute("question", question);
        model.addAttribute("answer", eightball.getAnswer());
        return "eightball";
    }
}
