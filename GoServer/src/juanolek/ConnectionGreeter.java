package juanolek;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionGreeter extends Thread {

    public boolean exitFlag = false;
    private IConnectionManagerFactory connectionManagerFactory;

    public ConnectionGreeter(IConnectionManagerFactory connectionManagerFactory){
        this.connectionManagerFactory = connectionManagerFactory;
    }

    @Override
    public void run(){

        while(!exitFlag){
            try {
                System.out.println("Waiting for players...");
                IConnectionManager connectionManager = connectionManagerFactory.getConnectionManager();
                Player newPlayer = new Player(connectionManager);
                Lobby.getInstance().addPlayer(newPlayer);
                newPlayer.startReceivingMessages();
                System.out.println("Past establishing conection");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
