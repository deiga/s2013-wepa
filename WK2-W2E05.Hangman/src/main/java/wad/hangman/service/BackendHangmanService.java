/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.hangman.service;

import javax.annotation.PostConstruct;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import wad.hangman.game.Game;

/**
 *
 * @author timosand
 */
@Service
public class BackendHangmanService implements HangmanService {
    
    RestTemplate restTemplate;
    Game game;
    
    @PostConstruct
    private void init() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }
    
    @Override
    public Game startGame() {
        return restTemplate.getForObject("http://t-avihavai.users.cs.helsinki.fi/hangman-backend/app/new", Game.class);
    }
    
    public Game getGameStatus(Game game) {
        return restTemplate.getForObject("http://t-avihavai.users.cs.helsinki.fi/hangman-backend/app/{gameId}/status", Game.class, game.getId());
    }
    
    @Override
    public Game guess(String gameId, Character guess) {
        return restTemplate.postForObject("http://t-avihavai.users.cs.helsinki.fi/hangman-backend/app/{gameId}/guess/{character}", null, Game.class, gameId, guess.toString());
    }
    
}
