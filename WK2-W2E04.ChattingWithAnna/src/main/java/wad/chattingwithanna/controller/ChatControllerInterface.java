package wad.chattingwithanna.controller;

import org.springframework.ui.Model;

public interface ChatControllerInterface {
    String addMessage(String message);
    String list(Model model);
}
