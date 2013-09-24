package wad.hangman;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

public class HangmanStatus {

    private String id;
    private List<String> used;
    @JsonIgnore
    private String realWord;
    private String word;
    private String state;
    private Integer wrong;
    private Integer remaining;
    private Integer length;

    // no-params constructor is needed for serialization
    public HangmanStatus() {
        state = "START";
        word = "________";
        wrong = 0;
        remaining = 7;
        length = 8;
        used = new ArrayList<String>();
    }

    public HangmanStatus(String gameId) {
        this();
        id = gameId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUsed() {
        return used;
    }

    public void setUsed(List<String> used) {
        this.used = used;
    }

    public String getRealWord() {
        return realWord;
    }

    public void setRealWord(String realWord) {
        this.realWord = realWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "HangmanStatus{" + "id=" + id + ", used=" + used + ", realWord=" + realWord + ", word=" + word + ", state=" + state + ", wrong=" + wrong + ", remaining=" + remaining + ", length=" + length + '}';
    }
}
