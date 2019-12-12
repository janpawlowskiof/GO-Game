package juanolek;

import juanolek.exceptions.GameNotExistingException;
import juanolek.exceptions.NoSlotsAvailableException;
import juanolek.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Lobby {

    private static Lobby instance = null;
    private Lobby(){}

    private HashMap<UUID, Player> players = new HashMap<>();
    private HashMap<UUID, Game> games = new HashMap<>();

    public synchronized void addPlayer(Player newPlayer)
    {
        players.put(newPlayer.getUuid(), newPlayer);
        newPlayer.setPlayerStrategy(new LobbyPlayerStrategy());
        newPlayer.sendMessage(new Message("showlobby", ""));
        newPlayer.sendMessage(new Message("Info", "Welcome in lobby " + newPlayer.getUuid().toString()));
    }

    public synchronized void createGame(Player player){
        removePlayer(player);
        Game newGame = new Game(19, player);
        games.put(newGame.getUuid(), newGame);
    }

    public synchronized void removePlayer(Player player){
        players.remove(player);
    }

    public synchronized void removeGame(Game game){
        games.remove(game);
    }

    public synchronized void addPlayerToGame(Player player, String gameUuid) throws GameNotExistingException, NoSlotsAvailableException {
        UUID uuid;
        try{
            uuid = UUID.fromString(gameUuid);
        }
        catch (Exception ex){
            throw new GameNotExistingException();
        }

        Game game = games.get(uuid);
        if(game == null){
            throw new GameNotExistingException();
        }
        game.addPlayer(player);
    }


    public HashMap<UUID, Player> getLobbyPlayers(){
        return players;
    }

    public HashMap<UUID, Game> getGames(){
        return games;
    }

    public synchronized Message getLobbyPlayersMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        players.forEach((uuid, player) -> {
            stringBuilder.append(",");
            stringBuilder.append(player.getUuid().toString());
        });
        return new Message("lobbyPlayers", stringBuilder.toString().substring(1));
    }

    public synchronized Message getGamesMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        games.forEach((uuid, game) -> {
            stringBuilder.append(",");
            stringBuilder.append(game.getUuid().toString());
        });
        return new Message("games", stringBuilder.toString().substring(1));
    }

    public static synchronized Lobby getInstance(){
        if(instance == null){
            instance = new Lobby();
        }
        return instance;
    }
}
