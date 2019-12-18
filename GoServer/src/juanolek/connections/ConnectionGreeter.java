package juanolek.connections;

import juanolek.Lobby;
import juanolek.player.Player;

import java.io.IOException;

public class ConnectionGreeter extends Thread {

    private final IConnectionManagerFactory connectionManagerFactory;
    private boolean exitFlag = false;

    public ConnectionGreeter(IConnectionManagerFactory connectionManagerFactory){
        this.connectionManagerFactory = connectionManagerFactory;
    }

    public void close(){
        exitFlag = true;
        connectionManagerFactory.closeConnections();
    }

    @Override
    public void run(){

        while(!exitFlag){
            try {
                System.out.println("Waiting for players...");
                IConnectionManager connectionManager = connectionManagerFactory.getConnectionManager();
                if(connectionManager == null)
                    return;
                Player newPlayer = new Player("Player", connectionManager);
                Lobby.getInstance().addPlayer(newPlayer);
                newPlayer.startReceivingMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
