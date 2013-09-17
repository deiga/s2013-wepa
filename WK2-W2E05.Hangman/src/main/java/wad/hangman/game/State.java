/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman.game;

/**
 *
 * @author timosand
 */
public enum State {
    START, GAMING, WIN, LOSE;
    
    public static State fromString(String value) {
        for (State state : values()) {
            if (state.name().equalsIgnoreCase(value)) {
                return state;
            }
        }
        return null;
    }
}
