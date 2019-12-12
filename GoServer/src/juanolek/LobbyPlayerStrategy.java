package juanolek;

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
            Lobby.getInstance().addPlayerToGame(sender, message.getValue());
        }
        else{
            System.out.println("Nieznany header " + message.getHeader());
        }
    }
}
