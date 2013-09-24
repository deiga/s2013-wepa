package wad.hangman;

public interface HangmanGameService {
    HangmanStatus initGame();
    HangmanStatus getStatus(String gameId);
    HangmanStatus guess(String gameId, String character);
}
