package wad.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Service;

@Service
public class InMemoryWordService implements WordService {

    private List<String> words = new ArrayList<String>() {
        {
            // DO NOT CHANGE THESE WORDS!
            String w =
                    "sentence\n"
                    + "together\n"
                    + "children\n"
                    + "mountain\n"
                    + "chipmunk\n"
                    + "crashing\n"
                    + "drinking\n"
                    + "insisted\n"
                    + "insulted\n"
                    + "invented\n"
                    + "squinted\n"
                    + "standing\n"
                    + "swishing\n"
                    + "talented\n"
                    + "whiplash\n"
                    + "complain\n"
                    + "granddad\n"
                    + "sprinkle\n"
                    + "surprise\n"
                    + "umbrella\n"
                    + "anything\n"
                    + "anywhere\n"
                    + "baseball\n"
                    + "birthday\n"
                    + "bluebird\n"
                    + "cheerful\n"
                    + "colorful\n"
                    + "daylight\n"
                    + "doghouse\n"
                    + "driveway\n"
                    + "everyone\n"
                    + "faithful\n"
                    + "flagpole\n"
                    + "graceful\n"
                    + "grateful\n"
                    + "homemade\n"
                    + "homework\n"
                    + "housefly\n"
                    + "kickball\n"
                    + "kingfish\n"
                    + "knockout\n"
                    + "knothole\n"
                    + "lipstick\n"
                    + "lunchbox\n"
                    + "newscast\n"
                    + "nickname\n"
                    + "peaceful\n"
                    + "sailboat\n"
                    + "saturday\n"
                    + "shameful\n"
                    + "sidewalk\n"
                    + "snowball\n"
                    + "splendid\n"
                    + "suitcase\n"
                    + "sunblock\n"
                    + "sunshine\n"
                    + "swimming\n"
                    + "thankful\n"
                    + "thinnest\n"
                    + "thursday\n"
                    + "whatever\n"
                    + "whenever\n"
                    + "windmill\n"
                    + "american\n"
                    + "possible\n"
                    + "suddenly\n"
                    + "airplane\n"
                    + "alphabet\n"
                    + "bathroom\n"
                    + "favorite\n"
                    + "medicine\n"
                    + "december\n"
                    + "dinosaur\n"
                    + "elephant\n"
                    + "February\n"
                    + "football\n"
                    + "forehead\n"
                    + "headache\n"
                    + "hospital\n"
                    + "lollipop\n"
                    + "november\n"
                    + "outdoors\n"
                    + "question\n"
                    + "railroad\n"
                    + "remember\n"
                    + "sandwich\n"
                    + "scissors\n"
                    + "shoulder\n"
                    + "softball\n"
                    + "tomorrow\n"
                    + "upstairs\n"
                    + "vacation\n"
                    + "restroom";

            addAll(Arrays.asList(w.split("\\s+")));
        }
    };

    @Override
    public String getWord() {
        return words.get(new Random().nextInt(words.size()));
    }

    @Override
    public List<String> getWordOptions(List<String> existingGuesses, String newGuess) {
        List<String> wordsCopy = new ArrayList(words);
        List<String> removed;
        for (String character : existingGuesses) {
            removed = new ArrayList<String>();
            final Iterator<String> iterator = wordsCopy.iterator();
            
            while (iterator.hasNext()) {
                String word = iterator.next();
                if (word.contains(character)) {
                    iterator.remove();
                    removed.add(word);
                }
            }
            if (wordsCopy.isEmpty()) {
                wordsCopy.addAll(removed);
                break;
            }
        }
        
        removed = new ArrayList<String>();
        final Iterator<String> iterator = wordsCopy.iterator();
            
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (word.contains(newGuess)) {
                iterator.remove();
                removed.add(word);
            }
        }
        if (wordsCopy.isEmpty()) {
            wordsCopy.addAll(removed);

        }
        
        return wordsCopy;
    }

}
