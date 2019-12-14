package juanolek;

import java.util.UUID;

public class Player implements IMessageReceiver{

    private IConnectionManager connectionManager;
    private IPlayerStrategy playerStrategy;
    private final UUID uuid;

    public Player(IConnectionManager connectionManager){
        this.connectionManager = connectionManager;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid(){
        return uuid;
    }

    public void startReceivingMessages(){
        this.connectionManager.startListening(this);
    }
    public void sendMessage(Message message){
        connectionManager.sendMessage(message);
    }

    public void setPlayerStrategy(IPlayerStrategy playerStrategy){
        this.playerStrategy = playerStrategy;
    }

    @Override
    public void receive(Message message) {
        if(playerStrategy == null){
            System.out.println("Brak strategii dla gracza");
            return;
        }
        if(message == null){
            playerStrategy.forceQuit(this);
            try{
                connectionManager.disconnect();
            }
            catch(Exception ex){
                System.out.println(ex);
            }
        }
        else{
            playerStrategy.handleMessage(message, this);
        }
    }
}
