/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.chattingwithanna.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.chattingwithanna.service.ChatBot;
import wad.chattingwithanna.service.MessageService;

/**
 *
 * @author timosand
 */
@Controller
public class ChatController implements ChatControllerInterface {
    
    @Autowired
    MessageService messages;
    @Autowired
    ChatBot bot;

    @Override
    @RequestMapping(value = "add-message", method = RequestMethod.POST)
    public String addMessage(@RequestParam(value = "message", required = false) String message) {
        if (message != null && !message.isEmpty()) {
            messages.addMessage("You:" + message);
            try {
                messages.addMessage(bot.getName() + ": " + bot.getAnswerForQuestion(message));
            } catch (IOException ex) {
                messages.addMessage(bot.getName() + ": " + ex.getLocalizedMessage());
            }
        }
        return "redirect:list";
    }

    @Override
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("messages", messages.list());
        return "list";
    }
    
}
