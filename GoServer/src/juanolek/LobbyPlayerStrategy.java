package juanolek;

public class LobbyPlayerStrategy implements IPlayerStrategy {

    @Override
    public void handleMessage(Message message, Player sender) {
        if(message.getHeader().equals("exit")){
            //TODO: Wyrzucenie gracza
        }
        else if(message.getHeader().equals("getLobbyPlayers")){
            sender.sendMessage(Lobby.getInstance().getLobbyPlayersMessage());
        }
        else if(message.getHeader().equals("getGames")){
            sender.sendMessage(Lobby.getInstance().getGames());
        }
        else if(message.getHeader().equals("createGame")){
            Lobby.getInstance().createGame(sender);
        }
        else if(message.getHeader().equals("joinGame")){
            Lobby.getInstance().addPlayerToGame(sender, message.getValue());
        }
        else{
            System.out.println("Nieznany header " + message.getHeader());
        }
    }
}
