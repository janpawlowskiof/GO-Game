package juanolek.gui.lobby;

import juanolek.client.IMessageReceiver;
import juanolek.client.Message;

public class lobbyController implements IMessageReceiver {

    @Override
    public void receive(Message message) {
        System.out.println("Wyjebane " + message.toString());
    }
}
