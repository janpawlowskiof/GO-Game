package juanolek;

import java.io.*;
import java.net.Socket;

public class Client implements IMessageReceiver {

    public Client(){
        System.out.println("Testing");
        Socket clientSocket = null;
        try {
            clientSocket = new Socket("localhost", 6666);

            BufferedReader consoleIn = new BufferedReader( new InputStreamReader(System.in) );
            IConnectionManager connectionManager = new TcpConnectionManager(clientSocket);
            connectionManager.startListening(this);

            while(true){
                String header = consoleIn.readLine();
                String value = consoleIn.readLine();
                if(header.equals("e"))
                    break;
                connectionManager.sendMessage(new Message(header, value));
            }

            //System.out.println(bufIn.readLine());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void receive(Message message) {
        System.out.println("Received a message:" + message.toString());
    }
}
