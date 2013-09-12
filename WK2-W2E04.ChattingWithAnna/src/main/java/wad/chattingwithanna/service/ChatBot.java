package wad.chattingwithanna.service;

import java.io.IOException;

public interface ChatBot {
    String getName();
    String getAnswerForQuestion(String question) throws IOException;
}
