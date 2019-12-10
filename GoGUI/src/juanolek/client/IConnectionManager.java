package juanolek.client;

import java.io.IOException;

public interface IConnectionManager {
    abstract void disconnect() throws IOException;
    abstract void sendMessage(Message message);
    abstract void startListening(IMessageReceiver receiver);
}
