package juanolek;

public class Player implements IMessageReceiver{

    private IConnectionManager connectionManager;
    private IPlayerStrategy playerStrategy;

    public void establishConnection(IConnectionManager connectionManager){
        System.out.println("Player connected!");

        this.connectionManager = connectionManager;
        this.connectionManager.startListening(this);
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

        playerStrategy.handleMessage(message);
    }
}
