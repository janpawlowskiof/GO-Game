package juanolek;

import juanolek.connections.ConnectionGreeter;
import juanolek.connections.TcpConnectionManagerFactory;

import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args){
        System.out.println("Starting server...\n");

        ConnectionGreeter connectionGreeter = null;
        try {
            System.out.println("Creating conneciton greeter");
            connectionGreeter = new ConnectionGreeter(new TcpConnectionManagerFactory(6666));
            System.out.println("Done!");
        } catch (IOException e) {
            System.out.println("Error creating connection manager :" + e.getMessage());
        }
        System.out.println("Starting server");
        connectionGreeter.start();

        System.out.println("Press enter to end");
        Scanner s = new Scanner(System.in);
        s.nextLine();
        System.out.println("Ending");

        connectionGreeter.close();
        System.exit(0);
    }
}
