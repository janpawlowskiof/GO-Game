package juanolek;

import java.util.ArrayList;

public class Lobby {

    private static Lobby instance = null;
    private Lobby(){}

    private ArrayList<Player> players = new ArrayList<>();

    public synchronized void addPlayer(Player newPlayer)
    {
        players.add(newPlayer);
        newPlayer.setPlayerStrategy(new LobbyPlayerStrategy());
    }
    public synchronized void removePlayer(Player newPlayer){players.remove(newPlayer);}
    public synchronized Message getLobbyPlayers()
    {
        return null;
    }
    public synchronized Message getGames()
    {
        return null;
    }

    public static synchronized Lobby getInstance(){
        if(instance == null){
            instance = new Lobby();
        }
        return instance;
    }
}
