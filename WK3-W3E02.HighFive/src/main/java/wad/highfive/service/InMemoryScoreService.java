package wad.highfive.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import wad.highfive.data.Game;
import wad.highfive.data.Score;

@Service
public class InMemoryScoreService implements ScoreService {

    private Map<Long, Score> scores;
    private Map<Game, List<Score>> scoresByGame;
    private Long idCounter;

    @PostConstruct
    private void init() {
        scores = new TreeMap<Long, Score>();
        scoresByGame = new HashMap<Game, List<Score>>();

        idCounter = 1L;
    }

    @Override
    public Score create(Score object) {
        object.setId(idCounter);
        idCounter++;

        scores.put(object.getId(), object);
        if (!scoresByGame.containsKey(object.getGame())) {
            scoresByGame.put(object.getGame(), new ArrayList<Score>());
        }

        scoresByGame.get(object.getGame()).add(object);

        return object;
    }

    @Override
    public Score findById(Long id) {
        return scores.get(id);
    }

    @Override
    public void delete(Score object) {
        scores.remove(object.getId());
        scoresByGame.get(object.getGame()).remove(object);
    }

    @Override
    public void delete(Long id) {
        delete(findById(id));
    }

    @Override
    public Collection<Score> findAll() {
        return scores.values();
    }

    @Override
    public Collection<Score> findByGame(Game game) {
        return scoresByGame.get(game);
    }

    @Override
    public Score findByGameAndId(Game game, Long id) {
        Score score = scores.get(id);
        if(score == null || !score.getGame().equals(game)) {
            return null;
        }
        
        return score;
    }
}
