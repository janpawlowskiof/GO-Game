package juanolek;

import java.io.IOException;

public interface IConnectionManagerFactory {
    IConnectionManager getConnectionManager() throws IOException;
}
