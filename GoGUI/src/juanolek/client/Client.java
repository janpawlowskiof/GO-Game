package juanolek.client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements IMessageReceiver {

    private IConnectionManager connectionManager = null;
    private IMessageReceiver messageReceiver = null;

    public void connect(String host, int port){
        try {
            Socket clientSocket = new Socket(host, port);
            connectionManager = new TcpConnectionManager(clientSocket);
            connectionManager.startListening(this);
        }
        catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to a server! Unknown host!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't connect to a server!");
        }
    }

    public void disconnect(){
        connectionManager = null;
    }

    public void setMessageReceiver(IMessageReceiver messageReceiver){
        this.messageReceiver = messageReceiver;
    }

    public synchronized void sendMessage(Message message){
        connectionManager.sendMessage(message);
    }

    @Override
    public void receive(Message message) {
        if(message == null){
            try {
                connectionManager.disconnect();
                connectionManager = null;
                messageReceiver.receive(new Message("showlogin", ""));
            } catch (IOException e) {
                System.out.println("Error closing the socket");
            }
        }
        else if(messageReceiver != null){
            messageReceiver.receive(message);
        }
    }
}
