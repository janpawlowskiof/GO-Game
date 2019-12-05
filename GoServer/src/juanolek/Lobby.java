package juanolek;

import juanolek.exceptions.NoSlotsAvailableException;

import java.util.ArrayList;
import java.util.UUID;

public class Lobby {

    private static Lobby instance = null;
    private Lobby(){}

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Game> games = new ArrayList<>();

    public synchronized void addPlayer(Player newPlayer)
    {
        players.add(newPlayer);
        newPlayer.setPlayerStrategy(new LobbyPlayerStrategy());
        newPlayer.sendMessage(new Message("Info", "Welcome in lobby " + newPlayer.getUuid().toString()));
    }

    public synchronized void createGame(Player player){
        removePlayer(player);
        Game newGame = new Game(player);
        games.add(newGame);
    }

    public synchronized void removePlayer(Player player){
        players.remove(player);
    }

    public synchronized void removeGame(Game game){
        games.remove(game);
    }

    public synchronized void addPlayerToGame(Player player, String gameUuid){
        UUID uuid = UUID.fromString(gameUuid);
        for(Game game : games){
            if(game.getUuid().equals(uuid)){
                try {
                    game.addPlayer(player);
                }
                catch (NoSlotsAvailableException e) {
                    player.sendMessage(new Message("Info", e.getMessage()));
                }
            }
        }
    }


    public ArrayList<Player> getLobbyPlayers(){
        return players;
    }

    public ArrayList<Game> getGames(){
        return games;
    }

    public synchronized Message getLobbyPlayersMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for(Player player : players){
            if(!first)
                stringBuilder.append(",");
            stringBuilder.append(player.getUuid().toString());
            first = false;
        }
        return new Message("playersInfo", stringBuilder.toString());
    }

    public synchronized Message getGamesMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;
        for(Game game : games){
            if(!first)
                stringBuilder.append(",");

            stringBuilder.append(game.getUuid().toString());
            stringBuilder.append(":");
            stringBuilder.append(game.getPlayerCount());

            first = false;
        }
        return new Message("gamesInfo", stringBuilder.toString());
    }

    public static synchronized Lobby getInstance(){
        if(instance == null){
            instance = new Lobby();
        }
        return instance;
    }
}
