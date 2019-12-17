package juanolek;

import java.io.IOException;

public interface IConnectionManager {
    void disconnect() throws IOException;
    void sendMessage(Message message);
    void startListening(IMessageReceiver receiver);
}
