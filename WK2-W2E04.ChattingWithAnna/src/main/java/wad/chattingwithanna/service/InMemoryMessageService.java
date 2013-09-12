package wad.chattingwithanna.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InMemoryMessageService implements MessageService {
    private final List<String> messages;

    public InMemoryMessageService() {
        this.messages = new ArrayList<String>();
    }

    @Override
    public void addMessage(String message) {
        this.messages.add(message);
    }

    @Override
    public List<String> list() {
        return messages;
    }
}
