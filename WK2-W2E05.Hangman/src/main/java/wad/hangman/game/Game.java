/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman.game;

/**
 *
 * @author timosand
 */
public class Game {
    
    private String id;
    private String[] used;
    private String word;
    private State state;
    private Integer wrong;
    private Integer remaining;
    private Integer length;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getUsed() {
        return used;
    }

    public void setUsed(String[] used) {
        this.used = used;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
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
    
}
