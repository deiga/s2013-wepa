/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman.service;

import wad.hangman.game.Game;

/**
 *
 * @author timosand
 */
public interface HangmanService {

    public Game startGame();

    public Game guess(String gameId, Character guess);
    
}
