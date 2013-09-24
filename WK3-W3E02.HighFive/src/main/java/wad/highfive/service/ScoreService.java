package wad.highfive.service;

import java.util.Collection;
import wad.highfive.data.Game;
import wad.highfive.data.Score;

public interface ScoreService extends ServiceInterface<Score> {
    Collection<Score> findByGame(Game game);
    Score findByGameAndId(Game game, Long id);
}
