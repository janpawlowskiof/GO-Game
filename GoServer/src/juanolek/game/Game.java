package juanolek.game;

import juanolek.GamePlayerStrategy;
import juanolek.Lobby;
import juanolek.Message;
import juanolek.Player;
import juanolek.exceptions.NoSlotsAvailableException;

import java.util.UUID;

public class Game{

    private Player playerWhite = null;
    private Player playerBlack = null;
    private UUID uuid;
    private int size;

    public Game(int size, Player player){
        this.uuid = UUID.randomUUID();
        this.size = size;
        try {
            addPlayer(player);
        } catch (NoSlotsAvailableException e) {
            System.out.println("What a Terrible Failure Adding a player to a fresh game session");
        }
    }

    public void addPlayer(Player player) throws NoSlotsAvailableException{
        System.out.println("Player " + player.getUuid().toString() + " tries to join the game");

        if(playerWhite == null){
            playerWhite = player;
        }
        else if(playerBlack == null){
            playerWhite.sendMessage(new Message("Info", "A player " + player.getUuid().toString() + " has joined the game!"));
            playerBlack = player;
        }
        else{
            throw new NoSlotsAvailableException();
        }

        player.setPlayerStrategy(new GamePlayerStrategy(this));
        Lobby.getInstance().removePlayer(player);
        player.sendMessage(new Message("Info", "you have joined the game"));
        player.sendMessage(new Message("showboard", ""));
    }

    public void endSession(){
        if(playerWhite != null){
            playerWhite.sendMessage(new Message("Info", "Game session has been closed"));
            Lobby.getInstance().addPlayer(playerWhite);
        }
        if(playerBlack != null){
            playerWhite.sendMessage(new Message("Info", "Game session has been closed"));
            Lobby.getInstance().addPlayer(playerBlack);
        }
        Lobby.getInstance().removeGame(this);
    }

    public Message getOpponentInfo(Player player){
        if(player == playerWhite){
            return new Message("opponentInfo", playerBlack.getUuid().toString());
        }
        else{
            return new Message("opponentInfo", playerWhite.getUuid().toString());
        }
    }

    public UUID getUuid(){
        return uuid;
    }
    public int getPlayerCount(){
        return (playerWhite != null ? 1 : 0) + (playerBlack != null ? 1 : 0);
    }
}
