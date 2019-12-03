package juanolek;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionGreeter extends Thread {

    public final int port = 6666;
    public boolean exitFlag = false;
    private ServerSocket serverSocket;

    @Override
    public void run(){

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error opening server socket");
            return;
        }

        while(!exitFlag){
            try {
                System.out.println("Waiting for players...");
                Player newPlayer = new Player();
                Socket socket = serverSocket.accept();
                Lobby.getInstance().addPlayer(newPlayer);
                newPlayer.establishConnection(new TcpConnectionManager(socket));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
