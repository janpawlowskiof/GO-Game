package juanolek.connections;

import java.io.IOException;

interface IConnectionManagerFactory {
    IConnectionManager getConnectionManager() throws IOException;
    void closeConnections();
}
