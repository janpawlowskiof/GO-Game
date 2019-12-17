package juanolek;

import java.io.IOException;

interface IConnectionManagerFactory {
    IConnectionManager getConnectionManager() throws IOException;
}
