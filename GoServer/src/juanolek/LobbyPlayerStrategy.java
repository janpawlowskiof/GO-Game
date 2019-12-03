package juanolek;

public class LobbyPlayerStrategy implements IPlayerStrategy {

    @Override
    public void handleMessage(Message message) {
        if(message.getHeader().equals("exit")){
            System.out.println("Jeszcze nie umiem cię wyjebać ale ok");
        }
        else if(message.getHeader().equals("join")){
            System.out.println("Przylaczam do " + message.getValue());
        }
        else{
            System.out.println("Nieznany header " + message.getHeader());
        }
    }
}
