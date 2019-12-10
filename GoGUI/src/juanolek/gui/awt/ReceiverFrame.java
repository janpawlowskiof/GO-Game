package juanolek.gui.awt;

import juanolek.client.IMessageReceiver;
import juanolek.client.Message;

import javax.swing.*;

public abstract class ReceiverFrame extends JFrame implements IMessageReceiver {
    @Override
    public abstract void receive(Message message);
}
