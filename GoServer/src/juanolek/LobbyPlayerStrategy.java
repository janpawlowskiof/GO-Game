package juanolek;

import juanolek.exceptions.GameNotExistingException;
import juanolek.exceptions.NoSlotsAvailableException;

public class LobbyPlayerStrategy implements IPlayerStrategy {

    @Override
    public void handleMessage(Message message, Player sender) {
        if(message.getHeader().equals("exit")){
            //TODO: Wyrzucenie gracza
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
}
