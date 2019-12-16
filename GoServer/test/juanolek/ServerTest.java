package juanolek;

import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.Random;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void testServerResistance() throws IOException {


        Random randomByte = new Random(100);
        byte[] randomBytes = new byte[500];
        randomByte.nextBytes(randomBytes);
        randomBytes[499]='\n';

        ConnectionGreeter connectionGreeter = null;
        connectionGreeter = new ConnectionGreeter(new TcpConnectionManagerFactory(6666));
        connectionGreeter.start();

        Socket testSocket = new Socket("localhost",6666);

        BufferedReader in = new BufferedReader( new InputStreamReader( testSocket.getInputStream()) );
        DataOutputStream out = new DataOutputStream(testSocket.getOutputStream());

        in.readLine();
        in.readLine();

        out.write(randomBytes);

        //in.readLine();

        in.close();
        out.close();
        testSocket.close();

    }


    @Test
    public void testUnexpectedDisconnect() throws IOException {

        ConnectionGreeter connectionGreeter = null;
        connectionGreeter = new ConnectionGreeter(new TcpConnectionManagerFactory(6666));
        connectionGreeter.start();

        Socket socket1 = new Socket("127.0.0.1",6666);

        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( socket1.getInputStream()) );
        BufferedWriter bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket1.getOutputStream()) );

        bufferedReader.readLine();
        bufferedReader.readLine();

        assertFalse(Lobby.getInstance().getLobbyPlayers().isEmpty());

        bufferedWriter.write("creategame"+'\t'+""+'\n');
        bufferedWriter.newLine();
        bufferedWriter.flush();

        bufferedReader.readLine();

        bufferedReader.close();
        bufferedWriter.close();
        socket1.close();

    }

}