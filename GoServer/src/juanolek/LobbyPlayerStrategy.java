package juanolek;

import juanolek.exceptions.GameNotExistingException;
import juanolek.exceptions.NoSlotsAvailableException;
import juanolek.game.Game;

public class LobbyPlayerStrategy implements IPlayerStrategy {

    @Override
    public void handleMessage(Message message, Player sender) {
        if(message.getHeader().equals("exit")){
            //TODO: exit player
            //Lobby.getInstance().removePlayer(sender);
        }
        else if(message.getHeader().equals("getlobbyplayers")){
            sender.sendMessage(Lobby.getInstance().getLobbyPlayersMessage());
        }
        else if(message.getHeader().equals("getgames")){
            sender.sendMessage(Lobby.getInstance().getGamesMessage());
        }
        else if(message.getHeader().equals("creategame")){
            Lobby.getInstance().createGame(sender);
        }
        else if(message.getHeader().equals("creategamewithbot")){
            Lobby.getInstance().createGameWithBot(sender);
        }
        else if(message.getHeader().equals("joingame")){
            try {
                Lobby.getInstance().addPlayerToGame(sender, message.getValue());
            } catch (GameNotExistingException e) {
                sender.sendMessage(new Message("Info", "Game" + message.getValue() + "does not exist"));
            } catch (NoSlotsAvailableException e) {
                sender.sendMessage(new Message("Info", "There are no empty slots available in that game"));
            }
        }
        else{
            System.out.println("Nieznany header " + message.getHeader());
        }
    }

    @Override
    public void forceQuit(Player sender) {
        Lobby.getInstance().removePlayer(sender);
    }
}
