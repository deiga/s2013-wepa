package wad.chattingwithanna.service;

import java.util.List;

public interface MessageService {
    void addMessage(String message);
    List<String> list();
}
