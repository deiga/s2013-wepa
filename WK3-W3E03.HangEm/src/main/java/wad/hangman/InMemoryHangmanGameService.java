package wad.hangman;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InMemoryHangmanGameService implements HangmanGameService {

    @Autowired
    private WordService wordService;
    private Map<String, HangmanStatus> games;

    @PostConstruct
    private void init() {
        games = new TreeMap<String, HangmanStatus>();
    }

    @Override
    public HangmanStatus initGame() {
        HangmanStatus game = new HangmanStatus();
        game.setId(UUID.randomUUID().toString().substring(0, 8));
        game.setRealWord(wordService.getWord());

        games.put(game.getId(), game);
        return game;
    }

    @Override
    public HangmanStatus getStatus(String gameId) {
        return games.get(gameId);
    }

    @Override
    public HangmanStatus guess(String gameId, String character) {
        HangmanStatus status = games.get(gameId);
        if (!isValidGuess(status, character)) {
            return status;
        }
        // ADD THE "IMPROVEMENTS" HERE
        List<String> wordOptions = wordService.getWordOptions(status.getUsed(), character);
        status.setRealWord(wordOptions.get(Double.valueOf(Math.random()*(wordOptions.size()-1)).intValue()));
        status.getUsed().add(character);

        if (!status.getRealWord().contains(character)) {
            status.setRemaining(status.getRemaining() - 1);
            status.setWrong(status.getWrong() + 1);

            if (status.getRemaining() <= 0) {
                status.setWord(status.getRealWord());
                status.setState("LOSE");
            }

            return status;
        }

        status.setWord(generateMaskedWord(status));

        if (!status.getWord().contains("_")) {
            status.setState("WIN");
        } else {
            status.setState("GAMING");
        }

        return status;
    }

    private boolean isValidGuess(HangmanStatus status, String character) {
        if (status == null) {
            return false;
        }

        if (status.getState().equals("WIN") || status.getState().equals("LOSE")) {
            return false;
        }

        if (character.length() != 1) {
            return false;
        }

        if (status.getUsed().contains(character)) {
            return false;
        }

        return true;
    }

    private String generateMaskedWord(HangmanStatus status) {
        StringBuilder maskedWord = new StringBuilder();
        for (char c : status.getRealWord().toCharArray()) {
            if (status.getUsed().contains("" + c)) {
                maskedWord.append(c);
            } else {
                maskedWord.append("_");
            }
        }
        
        return maskedWord.toString();
    }
}
