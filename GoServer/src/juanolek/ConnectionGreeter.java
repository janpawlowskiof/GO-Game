package juanolek;

import java.io.IOException;

class ConnectionGreeter extends Thread {

    private final IConnectionManagerFactory connectionManagerFactory;

    public ConnectionGreeter(IConnectionManagerFactory connectionManagerFactory){
        this.connectionManagerFactory = connectionManagerFactory;
    }

    @Override
    public void run(){

        boolean exitFlag = false;
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
