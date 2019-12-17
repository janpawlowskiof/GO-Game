package juanolek.gui;

import juanolek.client.Client;
import juanolek.client.IMessageReceiver;
import juanolek.client.Message;
import juanolek.gui.awt.AwtBoard;
import juanolek.gui.awt.AwtLobby;
import juanolek.gui.awt.AwtLogin;
import juanolek.gui.awt.AwtBoard;
import juanolek.gui.awt.ReceiverFrame;

import javax.swing.*;

public class GuiManager implements IMessageReceiver {

    private Client client;
    private ReceiverFrame viewFrameReceiver = null;

    public GuiManager(){
        client = new Client();
        showLoginWindow();
    }

    public void showBoard() {
        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new AwtBoard(this);
    }

    public void showLoginWindow() {
        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new AwtLogin(this);
    }


    public void showLobby() {
        if(viewFrameReceiver != null){
            viewFrameReceiver.dispose();
        }
        viewFrameReceiver = new AwtLobby(this);
    }

    @Override
    public void receive(Message message) {
        if(message == null){
            client.disconnect();
            showLoginWindow();
            JOptionPane.showMessageDialog(viewFrameReceiver, "Connection to the server lost!");
            return;
        }

        if(message.getHeader().equals("showlobby")){
            showLobby();
        }
        else if(message.getHeader().equals("showboard")){
            showBoard();
        }
        else if(message.getHeader().equals("showlogin")){
            showLoginWindow();
        }
        else if(message.getHeader().toLowerCase().equals("info") ||
                message.getHeader().toLowerCase().equals("invalidmoveinfo") ){
            JOptionPane.showMessageDialog(viewFrameReceiver, message.getValue());
        }
        else{
            viewFrameReceiver.receive(message);
        }
    }

    public void sendMessage(Message message){
        if(client != null) {
            client.sendMessage(message);
            System.out.println("Sending Message " + message.toString());
        }
    }

    public void connect(String ip, int port){
        client.setMessageReceiver(this);
        client.connect(ip, port);
    }
}
