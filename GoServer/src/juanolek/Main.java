package juanolek;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        System.out.println("Starting server...\n");

        ConnectionGreeter connectionGreeter = null;
        try {
            connectionGreeter = new ConnectionGreeter(new TcpConnectionManagerFactory(6666));
        } catch (IOException e) {
            System.out.println("Error creating connection manager :" + e.getMessage());
        }
        connectionGreeter.start();
    }
}
