package wad.highfive.service;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import wad.highfive.data.Game;

@Service
public class InMemoryGameService implements GameService {

    private Map<Long, Game> games;
    private Long idCounter;

    @PostConstruct
    private void init() {
        games = new TreeMap<Long, Game>();
        idCounter = 1L;
    }

    @Override
    public Game create(Game object) {
        object.setId(idCounter);
        idCounter++;

        games.put(object.getId(), object);
        return object;
    }

    @Override
    public Game findById(Long id) {
        return games.get(id);
    }

    @Override
    public void delete(Game object) {
        delete(object.getId());
    }

    @Override
    public void delete(Long id) {
        games.remove(id);
    }

    @Override
    public Collection<Game> findAll() {
        return games.values();
    }

    @Override
    public Game findByName(String name) {
        for (Game game : findAll()) {
            if (name.equals(game.getName())) {
                return game;
            }
        }

        return null;
    }
}
