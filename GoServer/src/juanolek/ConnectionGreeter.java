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
                Socket socket = serverSocket.accept();
                Player newPlayer = new Player(new TcpConnectionManager(socket));
                Lobby.getInstance().addPlayer(newPlayer);
                newPlayer.startReceivingMessages();
                System.out.println("Past establishing conection");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
