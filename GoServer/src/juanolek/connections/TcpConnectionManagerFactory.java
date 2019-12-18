package juanolek.connections;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TcpConnectionManagerFactory implements IConnectionManagerFactory {

    private ServerSocket serverSocket;

    public TcpConnectionManagerFactory(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public IConnectionManager getConnectionManager() throws IOException, SocketException {
        Socket socket = null;
        try{
            socket = serverSocket.accept();
            return new TcpConnectionManager(socket);
        }
        catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void closeConnections() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Server socket already closed");
        }
    }
}
