/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.passwordgenerator;

import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author timosand
 */
@Controller
public class PasswordGeneratorController {
    
    public String generatePassword() {
        return UUID.randomUUID().toString();
    }
    
    @RequestMapping(value = "new-password", method = RequestMethod.GET)
    public String newPassword(Model model) {
        model.addAttribute("password", generatePassword());
        
        return "password";
    }
}
