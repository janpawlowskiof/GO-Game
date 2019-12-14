package juanolek.game;

import juanolek.GamePlayerStrategy;
import juanolek.Lobby;
import juanolek.Message;
import juanolek.Player;
import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.NoSlotsAvailableException;
import juanolek.exceptions.TrashDataException;

import java.util.List;
import java.util.UUID;

public class Game{

    private Player playerWhite = null;
    private Player playerBlack = null;
    private GameLogic gameLogic;
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
        this.gameLogic = new GameLogic(size);
    }

    public void setPawn(int x, int y, Player player){
        GamePawnType playerType = null;
        if(player == playerWhite)
            playerType = GamePawnType.White;
        else if(player == playerBlack){
            playerType = GamePawnType.Black;
        }
        else{
            System.out.println("Player not in game session tried moving pawn! Terminating server");
            //close
        }
        
        try {
            List<GameBoardChange> boardChanges = gameLogic.setPawn(x, y, playerType);
            for(GameBoardChange boardChange : boardChanges){
                if(boardChange.getChangeType() == GameBoardChange.ChangeType.Add){
                    Message message = new Message(boardChange.getPawnType() == GamePawnType.White ? "setWhitePawn" : "setBlackPawn", boardChange.getX()+","+boardChange.getY());
                    if(playerWhite != null) playerWhite.sendMessage(message);
                    if(playerBlack != null) playerBlack.sendMessage(message);
                }
                else if(boardChange.getChangeType() == GameBoardChange.ChangeType.Delete){
                    Message message = new Message("deletePawn", boardChange.getX() + "," + boardChange.getY());
                    System.out.println("Sending message after delete" + message);
                    if(playerWhite != null) playerWhite.sendMessage(message);
                    if(playerBlack != null) playerBlack.sendMessage(message);
                }

            }

        } catch (InvalidMoveException e) {
            player.sendMessage(new Message("Info", e.getMessage()));
        } catch (TrashDataException e) {
            e.printStackTrace();
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
