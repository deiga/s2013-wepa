package wad.hangman;

import java.util.List;

public interface WordService {
    String getWord();
    
    List<String> getWordOptions(List<String> existingGuesses, String newGuess);
}
