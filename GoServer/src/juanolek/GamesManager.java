package juanolek;

import java.util.HashMap;
import java.util.UUID;

public class GamesManager {

    private static GamesManager instance = null;
    private HashMap<String, Game> games;
    private GamesManager(){
        games = new HashMap<>();
    }
    private static synchronized GamesManager getInstance(){
        if(instance == null)
            instance = new GamesManager();
        return instance;
    }

    public String createNewGame(){
        String key = UUID.randomUUID().toString();
        Game game = new Game();
        games.put(key, game);
        return key;
    }
}
