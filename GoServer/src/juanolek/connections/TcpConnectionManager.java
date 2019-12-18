package juanolek.connections;

import juanolek.IMessageReceiver;
import juanolek.Message;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class TcpConnectionManager implements IConnectionManager {

    private final Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public TcpConnectionManager(Socket socket){
        this.socket = socket;
        try {
            bufferedReader = new BufferedReader( new InputStreamReader( socket.getInputStream()) );
            bufferedWriter = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream()) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TcpListener extends Thread{

        final boolean exitFlag = false;
        final IMessageReceiver receiver;

        TcpListener(IMessageReceiver receiver){
            this.receiver = receiver;
        }

        @Override
        public void run(){
            try {
                while(!exitFlag){

                    String line = bufferedReader.readLine();
                    if(line == null){
                        receiver.receive(null);
                        return;
                    }

                    int separatorIndex = line.indexOf('\t');
                    Message message;
                    if(separatorIndex < 0 || separatorIndex >= line.length())
                        message = new Message(line, "");
                    else
                        message = new Message(line.substring(0, separatorIndex), line.substring(separatorIndex + 1));

                    receiver.receive(message);
                }
            }
            catch (IOException e) {
                System.out.println("Error 53 9792 " + e.getMessage() + "\n\n" + Arrays.toString(e.getStackTrace()));
            }
        }
    }


    @Override
    public void disconnect() throws IOException {
        socket.close();
    }

    @Override
    public void sendMessage(Message message) {
        String value = "test";
        try {
            bufferedWriter.write(message.getHeader() + '\t' + message.getValue());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startListening(IMessageReceiver receiver) {
        TcpListener tcpListener = new TcpListener(receiver);
        tcpListener.start();
    }
}
