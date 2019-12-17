package juanolek;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpConnectionManagerFactory implements IConnectionManagerFactory {

    private ServerSocket serverSocket;

    public TcpConnectionManagerFactory(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public IConnectionManager getConnectionManager() throws IOException {
        return new TcpConnectionManager(serverSocket.accept());
    }
}
