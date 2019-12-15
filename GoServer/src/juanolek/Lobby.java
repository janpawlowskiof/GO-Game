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

        Message playersMessage = getLobbyPlayersMessage();
        players.forEach((uuid, player)->{
            player.sendMessage(playersMessage);
        });
    }

    public synchronized void createGame(Player player){
        removePlayer(player);
        Game newGame = new Game(19, player);
        games.put(newGame.getUuid(), newGame);

        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, game)->{
            game.sendMessage(gamesMessage);
        });
    }

    public synchronized void removePlayer(Player removedPlayer){
        players.remove(removedPlayer.getUuid());

        Message playersMessage = getLobbyPlayersMessage();
        players.forEach((uuid, player)->{
            player.sendMessage(playersMessage);
        });
    }

    public synchronized void removeGame(Game removedGame){
        games.remove(removedGame.getUuid());

        Message gamesMessage = getGamesMessage();
        players.forEach((uuid, game)->{
            game.sendMessage(gamesMessage);
        });
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
            stringBuilder.append(player.getNick());
        });
        return new Message("lobbyPlayers", stringBuilder.toString().substring(1));
    }

    public synchronized Message getGamesMessage() {
        if(games.size() == 0){
            return new Message("games", "");
        }

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
