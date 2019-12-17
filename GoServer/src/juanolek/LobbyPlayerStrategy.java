package juanolek;

import juanolek.exceptions.GameNotExistingException;
import juanolek.exceptions.NoSlotsAvailableException;

public class LobbyPlayerStrategy implements IPlayerStrategy {

    @Override
    public void handleMessage(Message message, Player sender) {
        switch (message.getHeader()) {
            case "getlobbyplayers":
                sender.sendMessage(Lobby.getInstance().getLobbyPlayersMessage());
                break;
            case "getgames":
                sender.sendMessage(Lobby.getInstance().getGamesMessage());
                break;
            case "creategame":
                Lobby.getInstance().createGame(sender);
                break;
            case "creategamewithbot":
                Lobby.getInstance().createGameWithBot(sender);
                break;
            case "joingame":
                try {
                    Lobby.getInstance().addPlayerToGame(sender, message.getValue());
                } catch (GameNotExistingException e) {
                    sender.sendMessage(new Message("Info", "Game" + message.getValue() + "does not exist"));
                } catch (NoSlotsAvailableException e) {
                    sender.sendMessage(new Message("Info", "There are no empty slots available in that game"));
                }
                break;
            default:
                System.out.println("Nieznany header " + message.getHeader());
                break;
        }
    }

    @Override
    public void forceQuit(Player sender) {
        Lobby.getInstance().removePlayer(sender);
    }
}
