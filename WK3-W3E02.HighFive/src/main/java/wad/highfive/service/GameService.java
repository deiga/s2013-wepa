package wad.highfive.service;

import wad.highfive.data.Game;

public interface GameService extends ServiceInterface<Game> {
    Game findByName(String name);
}
