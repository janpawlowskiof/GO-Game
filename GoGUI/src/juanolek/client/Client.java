package juanolek.client;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements IMessageReceiver {

    Socket clientSocket = null;
    BufferedReader consoleIn;
    IConnectionManager connectionManager = null;
    IMessageReceiver messageReceiver = null;

    public void connect(String host, int port){
        try {
            clientSocket = new Socket(host, port);
            connectionManager = new TcpConnectionManager(clientSocket);
            connectionManager.startListening(this);
        }
        catch (UnknownHostException ex) {
            ex.printStackTrace();
            System.out.println("Error!!!");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error!!!");
        }
    }

    public void setMessageReceiver(IMessageReceiver messageReceiver){
        this.messageReceiver = messageReceiver;
    }

    public synchronized void sendMessage(Message message){
        System.out.println("Get to Clinet...");
        connectionManager.sendMessage(message);
    }

    @Override
    public void receive(Message message) {
        System.out.println("Received a message:" + message.toString());
        if(messageReceiver != null){
            messageReceiver.receive(message);
        }
    }
}
