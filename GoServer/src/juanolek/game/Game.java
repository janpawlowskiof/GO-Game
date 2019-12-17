package juanolek.game;

import juanolek.*;
import juanolek.exceptions.InvalidMoveException;
import juanolek.exceptions.NoSlotsAvailableException;
import juanolek.exceptions.TrashDataException;

import java.util.List;
import java.util.UUID;

public class Game implements IEndGameHandler{

    private Player playerWhite = null;
    private Player playerBlack = null;
    private final GameLogic gameLogic;
    private final UUID uuid;

    public Game(int size, Player player){
        this.uuid = UUID.randomUUID();
        try {
            addPlayer(player);
        } catch (NoSlotsAvailableException e) {
            System.out.println("What a Terrible Failure Adding a player to a fresh game session");
        }
        this.gameLogic = new GameLogic(this, size);
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

            if(player == playerWhite) playerBlack.sendMessage(new Message("yourturn", ""));
            else playerWhite.sendMessage(new Message("yourturn", ""));

        } catch (InvalidMoveException e) {
            player.sendMessage(new Message("InvalidMoveInfo", e.getMessage()));
        } catch (TrashDataException e) {
            System.out.println("Trash data received");
        }
    }

    public void pass(Player player){
        try{
            if(player == playerWhite){
                if(gameLogic.pass(GamePawnType.White)){
                    playerBlack.sendMessage(new Message("info", "Your opponent passed"));
                    playerBlack.sendMessage(new Message("yourturn", ""));
                }
            }
            else{
                if (gameLogic.pass((GamePawnType.Black))){
                    playerWhite.sendMessage(new Message("info", "Your opponent passed"));
                    playerWhite.sendMessage(new Message("yourturn", ""));
                }
            }
        }
        catch(InvalidMoveException ex){
            player.sendMessage(new Message("info", ex.getMessage()));
        }
    }

    public void addPlayer(Player player) throws NoSlotsAvailableException{
        System.out.println("Player " + player.getNick() + " tries to join the game");
        Lobby.getInstance().removePlayer(player);
        player.setPlayerStrategy(new GamePlayerStrategy(this));
        player.sendMessage(new Message("showboard", ""));

        if(playerWhite == null){
            playerWhite = player;
            playerWhite.sendMessage(new Message("colorinfo", "white"));
        }
        else if(playerBlack == null){
            //playerWhite.sendMessage(new Message("Info", "A player " + player.getNick() + " has joined the game!"));
            playerBlack = player;
            playerWhite.sendMessage(new Message("OpponentInfo", playerBlack.getNick()));
            playerBlack.sendMessage(new Message("OpponentInfo", playerWhite.getNick()));
            playerBlack.sendMessage(new Message("colorinfo", "black"));
        }
        else{
            throw new NoSlotsAvailableException();
        }
    }

    public void endSession(boolean notifyPlayers){
        if(playerWhite != null){
            if(notifyPlayers)
                playerWhite.sendMessage(new Message("Info", "Game session has been closed"));
            if(!(playerWhite instanceof PlayerBot))
                Lobby.getInstance().addPlayer(playerWhite);
        }
        if(playerBlack != null){
            if(notifyPlayers)
                playerBlack.sendMessage(new Message("Info", "Game session has been closed"));
            if(!(playerBlack instanceof PlayerBot))
                Lobby.getInstance().addPlayer(playerBlack);
        }
        Lobby.getInstance().removeGame(this);
    }

    public Message getOpponentInfo(Player player){
        if(player == playerWhite){
            return new Message("opponentInfo", playerBlack.getNick());
        }
        else{
            return new Message("opponentInfo", playerWhite.getNick());
        }
    }

    public void kickPlayer(Player player){
        if(playerWhite.getUuid() == player.getUuid()){
            playerWhite = null;
        }
        else if(playerBlack.getUuid() == player.getUuid()){
            playerBlack = null;
        }
        endSession(true);
    }

    public UUID getUuid(){
        return uuid;
    }
    public int getPlayerCount(){
        return (playerWhite != null ? 1 : 0) + (playerBlack != null ? 1 : 0);
    }

    @Override
    public void handleEndGame(List<GameBoardChange> boardChanges) {

        for(GameBoardChange boardChange : boardChanges) {
            if (boardChange.getChangeType() == GameBoardChange.ChangeType.Delete) {
                Message message = new Message("deletePawn", boardChange.getX() + "," + boardChange.getY());
                System.out.println("Sending message after delete" + message);
                if (playerWhite != null) playerWhite.sendMessage(message);
                if (playerBlack != null) playerBlack.sendMessage(message);
            }
}

        int whitePoints = gameLogic.getWhitePoints();
        int blackPoints = gameLogic.getBlackPoints();

        playerWhite.sendMessage(new Message("yourScore", whitePoints + ""));
        playerWhite.sendMessage(new Message("opponentsScore", blackPoints + ""));
        playerBlack.sendMessage(new Message("yourScore", blackPoints + ""));
        playerBlack.sendMessage(new Message("opponentsScore", whitePoints + ""));

        if(whitePoints > blackPoints){
            playerWhite.sendMessage(new Message("info", "Game ended. You have won!"));
            playerBlack.sendMessage(new Message("info", "Game ended. You have lost!"));
        }
        else if(whitePoints < blackPoints){
            playerBlack.sendMessage(new Message("info", "Game ended. You have won!"));
            playerWhite.sendMessage(new Message("info", "Game ended. You have lost!"));
        }
        else{
            playerBlack.sendMessage(new Message("info", "Game ended. Draw!"));
            playerWhite.sendMessage(new Message("info", "Game ended. Draw!"));
        }

        endSession(false);
    }
}
