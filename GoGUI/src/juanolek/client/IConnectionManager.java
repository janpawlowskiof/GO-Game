package juanolek.client;

import java.io.IOException;

interface IConnectionManager {
    void disconnect() throws IOException;
    void sendMessage(Message message);
    void startListening(IMessageReceiver receiver);
}
